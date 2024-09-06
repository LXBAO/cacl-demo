package com.example.crawler.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.service.IBilBilDetailService;
import com.example.crawler.vo.BilBilExportVO;
import com.example.crawler.vo.Page;
import com.example.crawler.vo.Params;
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
import java.util.Map;


@Service("bilBilDetailService")
public class BilBilDetailService implements IBilBilDetailService {
    Logger log = LoggerFactory.getLogger(BilBilDetailService.class);
    ThreadLocal<Integer> currPage = new ThreadLocal<>();
    ThreadLocal<Integer> pageSize = new ThreadLocal<>();
    ThreadLocal<List<Object>> threadLocal = new ThreadLocal<>();

    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public List<Object> executor(BilBilExportVO bilBilExportVO, Page<Params> page) {
        if(threadLocal.get()== null){
            threadLocal.set(new ArrayList<>());
            currPage.set(page.getStartPage());
        }

        String url = "";
        try {
            CookieStore store = new BasicCookieStore();

            CloseableHttpClient client = HttpClients.custom().setDefaultCookieStore(store).build();
            CloseableHttpResponse response = null;
            while (true) {

                url = Constants.BILBIL_ARTICLE_DETAIL_URL.replace("{0}", page.getT().getId()).replace("{1}", currPage.get()+"");
                HttpGet httpget = new HttpGet(url);
                httpget.setHeader("User-Agent", Constants.USER_AGENT);
                httpget.addHeader(new BasicHeader("Cookie","SESSDATA="+page.getT().getToken()));
                response = client.execute(httpget);

                int success = response.getStatusLine().getStatusCode();
                // 解析数据返回内容
                if(Constants.SUCCESS == success){
                    JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));

                    JSONObject data =  jsonObject.getJSONObject("data");
                    JSONObject list = data.getJSONObject("list");
                    Map <String,JSONObject> tlist = list.getObject("tlist",Map.class);
                    if(currPage.get() == page.getStartPage()){
                        int count = 0;
                        for (Map.Entry<String,JSONObject> e : tlist.entrySet()) {

                            JSONObject obj = e.getValue();
                            count += obj.getInteger("count");

                        }
                        bilBilExportVO.setAweme_count(count);
                        pageSize.set(count%Constants.BILBIL_PAGE_SIZE==0?count/Constants.BILBIL_PAGE_SIZE:(count/Constants.BILBIL_PAGE_SIZE)+1);
                    }
                    threadLocal.get().addAll(list.getJSONArray("vlist"));
                    if(pageSize.get() == currPage.get() || (page.getEndPage() > 0 && currPage.get() == page.getEndPage())){
                        break;
                    }
                    currPage.set(currPage.get()+1);

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
        List<Object> list = new ArrayList<>();
        list.addAll(threadLocal.get());
        threadLocal.remove();
        return list;

    }

}
