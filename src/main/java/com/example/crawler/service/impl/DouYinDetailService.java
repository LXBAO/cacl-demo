package com.example.crawler.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.service.IDouYinDetailService;
import com.example.crawler.vo.DouYinExportVO;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("douYinDetailService")
public class DouYinDetailService implements IDouYinDetailService {
    Logger log = LoggerFactory.getLogger(DouYinService.class);
    ThreadLocal<String> index = new ThreadLocal<>();

    ThreadLocal<List<JSONObject>> threadLocal = new ThreadLocal<>();

    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public List<JSONObject> executor(DouYinExportVO douYinExportVO,String key) {
        if(threadLocal.get()== null){
            threadLocal.set(new ArrayList<>());
        }
        String url = "";
        try {
            CookieStore store = new BasicCookieStore();

            CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();
            CloseableHttpResponse response = null;
            while (true) {
                String max = index.get() == null ? "0" : index.get();
                url = Constants.DOUYIN_ARTICLE_DETAIL_URL.replace("{0}", key).replace("{1}", max);
                HttpGet httpget = new HttpGet(url);
                httpget.setHeader("User-Agent", Constants.USER_AGENT);

                response = client.execute(httpget);

                int success = response.getStatusLine().getStatusCode();
                // 解析数据返回内容
                if(Constants.SUCCESS == success){
                    JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                    JSONArray aweme_list = jsonObject.getJSONArray("aweme_list");
                    index.set(jsonObject.getString("max_cursor"));

                    if (aweme_list.size() > 0) {
                        for(Object jsonObject2: aweme_list){
                            JSONObject jsonObject3 = (JSONObject) jsonObject2;
                            JSONObject jsonObject4 = jsonObject3.getJSONObject("statistics");
                            threadLocal.get().add(jsonObject4);
                        }
                    }
                    Boolean bool = jsonObject.getBoolean("has_more");//是否有下一页
                    if(!bool){
                        break;
                    }

                }else {
                    log.error(url + "+:请求的状态码错误" + success);
                    throw new RemoteAccessException("根据抖音id请求用户异常");
                }

            }
        }
        catch (RemoteAccessException e) {
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (HttpHostConnectException e) {
            log.error( "连接异常url:" + url);
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("未知异常url：" + url + "错误日志" + e.getLocalizedMessage());
        }
        List<JSONObject> list = new ArrayList<>();
        list.addAll(threadLocal.get());
        threadLocal.remove();
        return list;

    }

}
