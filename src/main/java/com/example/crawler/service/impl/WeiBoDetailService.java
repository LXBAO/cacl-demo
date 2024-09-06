package com.example.crawler.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.service.IDouYinDetailService;
import com.example.crawler.service.IWeiBoDetailService;
import com.example.crawler.vo.DouYinExportVO;
import com.example.crawler.vo.Page;
import com.example.crawler.vo.Params;
import com.example.crawler.vo.WeiBoExportVO;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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


@Service("weiBoDetailService")
public class WeiBoDetailService implements IWeiBoDetailService {
    Logger log = LoggerFactory.getLogger(DouYinService.class);
    ThreadLocal<Integer> currPage = new ThreadLocal<>();

    ThreadLocal<JSONArray> threadLocal = new ThreadLocal<>();

    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public JSONArray executor(WeiBoExportVO weiBoExportVO, Page<Params> page) {

        if(threadLocal.get()== null){
            threadLocal.set(new JSONArray());
            currPage.set(page.getStartPage());
        }
        String url = "";
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = null;
            while (true) {

                url = Constants.WEIBO_ARTICLE_DETAIL_URL.replace("{0}", page.getT().getId()).replace("{1}", currPage.get()+"") ;
                HttpGet httpget = new HttpGet(url);
                httpget.setHeader("User-Agent", Constants.USER_AGENT);
                httpget.addHeader(new BasicHeader("Cookie","SUB="+page.getT().getToken()));
                response = client.execute(httpget);

                int success = response.getStatusLine().getStatusCode();
                // 解析数据返回内容
                if(Constants.SUCCESS == success){
                    JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                    JSONObject data = jsonObject.getJSONObject("data");

                    if(weiBoExportVO.getTotal_size()==0){
                        int total = data.getInteger("total");
                        weiBoExportVO.setTotal_size(total);
                    }
                   JSONArray objects =  data.getJSONArray("list");
                    threadLocal.get().addAll(data.getJSONArray("list"));
                    if(objects.size()==0 ||
                            threadLocal.get().size()>= weiBoExportVO.getTotal_size() ||
                            (page.getEndPage() > 0 && currPage.get() == page.getEndPage())){
                        break;
                    }

                    currPage.set(currPage.get()+1);

                }else {
                    log.error(url + "+:请求的状态码错误" + success);
                    throw new RemoteAccessException("微博详情数据抓取异常");
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
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(threadLocal.get());
        threadLocal.remove();
        return jsonArray;

    }

}
