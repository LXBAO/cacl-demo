package com.example.crawler.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.service.IBilBilService;
import com.example.crawler.service.IDouYinService;
import com.example.crawler.vo.BilBilExportVO;
import com.example.crawler.vo.DouYinExportVO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
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

@Service("bilBilService")
public class BilBilService implements IBilBilService {
    Logger log = LoggerFactory.getLogger(BilBilService.class);

    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public void executor(String userIdKey, String sessData,int index,BilBilExportVO bilBilExportVO) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url ="";
        if(index ==0 ){
            url = Constants.BILBIL_ARTICLE_URL + userIdKey;
        }else {
            url = Constants.BILBIL2_ARTICLE_URL + userIdKey;
        }

        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent", Constants.USER_AGENT);
            httpget.addHeader(new BasicHeader("Cookie","SESSDATA="+sessData));
            response = client.execute(httpget);
            int success = response.getStatusLine().getStatusCode();
            if (Constants.SUCCESS == success) {
                JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                JSONObject jsonObject2 = jsonObject.getJSONObject("data");
                if (jsonObject2 == null) {

                    throw new RuntimeException("根据bilbil id没有获取到用户信息");
                }
                if(index == 0){
                    JSONObject archive = jsonObject2.getJSONObject("archive");
                    int  favoriting_count = archive.getInteger("view");
                    bilBilExportVO.setFavoriting_count(favoriting_count);
                    executor(userIdKey, sessData,1, bilBilExportVO);
                }else {
                    int  follower = jsonObject2.getInteger("follower");
                    bilBilExportVO.setFollower_count(follower);
                }

            } else {
                log.error(url + " 请求的状态码错误" + success);
                throw new RemoteAccessException("根据根据bilbil id请求用户异常");
            }

        } catch (RemoteAccessException e) {
            throw new RemoteAccessException(e.getLocalizedMessage());
        }  catch (HttpHostConnectException e) {
            log.error( "连接异常 url:" + url);
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("未知异常url：" + url + "错误日志" + e.getLocalizedMessage());
        }

    }
}
