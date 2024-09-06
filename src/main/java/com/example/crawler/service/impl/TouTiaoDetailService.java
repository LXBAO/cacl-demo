package com.example.crawler.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.ToutiaoUtil;
import com.example.crawler.service.IDouYinDetailService;
import com.example.crawler.service.ITouTiaoDetailService;
import com.example.crawler.vo.DouYinExportVO;
import com.example.crawler.vo.TouTiaoExportVO;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie2;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("touTiaoDetailService")
public class TouTiaoDetailService implements ITouTiaoDetailService {
    Logger log = LoggerFactory.getLogger(DouYinService.class);
    ThreadLocal<String> index = new ThreadLocal<>();

    ThreadLocal<List<JSONObject>> threadLocal = new ThreadLocal<>();


    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public List<JSONObject> executor(TouTiaoExportVO touTiaoExportVO, String id,String sessionId) {
        if(threadLocal.get()== null){
            threadLocal.set(new ArrayList<>());
        }
        if(index.get()== null){
            index.set("0");
        }
        String url = "";
        BasicCookieStore store = new BasicCookieStore();
        CloseableHttpClient client = HttpClients.createDefault();


        try {

            CloseableHttpResponse response = null;
            while (true) {
                Map<String,String> ascp = ToutiaoUtil.getAsCp();
                String as = ascp.get("as");
                String cp = ascp.get("cp");
               url = Constants.TOUTIAO_ARTICLE_DETAIL_URL.replace("{0}",index.get()).
                       replace("{1}",id).replace("{2}",id)
                       .replace("{3}",as).replace("{4}",cp);

                HttpGet httpget = new HttpGet(url);
                httpget.addHeader(new BasicHeader("Cookie","sessionid="+sessionId));
                httpget.setHeader(new BasicHeader("User-Agent", Constants.USER_AGENT));
                httpget.setHeader(new BasicHeader("Content-Type", "application/json; UTF-8"));

                response = client.execute(httpget);
                int success = response.getStatusLine().getStatusCode();
                // 解析数据返回内容
                if(Constants.SUCCESS == success){


                    JSONObject ret = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                    String newMaxBehotTime = ret.getJSONObject("next").getString("max_behot_time");
                    index.set(newMaxBehotTime);
                    if (ret.getInteger("has_more")==0) {
                      break;
                    }
                    JSONArray articles = ret.getJSONArray("data");
                    for(Object obj: articles){
                        JSONObject js = (JSONObject)obj;
                        JSONObject js2 = new JSONObject();
                        js2.put("total_read_count",js.getInteger("total_read_count"));
                        js2.put("comment_count",js.getInteger("comment_count"));
                        threadLocal.get().add(js2);
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
