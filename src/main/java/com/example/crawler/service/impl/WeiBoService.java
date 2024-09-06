package com.example.crawler.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.service.IWeiBoService;
import com.example.crawler.vo.WeiBoExportVO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("weiBoService")
public class WeiBoService implements IWeiBoService {
    Logger log = LoggerFactory.getLogger(WeiBoService.class);

    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public WeiBoExportVO executor(String id) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url = Constants.WEIBO_ARTICLE_URL + id;
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent", Constants.USER_AGENT);

            response = client.execute(httpget);
            int success = response.getStatusLine().getStatusCode();
            if (Constants.SUCCESS == success) {
                JSONObject body = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                JSONObject data = body.getJSONObject("data");
                JSONObject jsonObject = data.getJSONObject("userInfo");
                if (jsonObject == null) {

                    throw new RuntimeException("根据微博id有获取到用户信息");
                }
                String following_count = jsonObject.getString("followers_count");
                String screen_name = jsonObject.getString("screen_name");


                JSONObject tabsInfo = data.getJSONObject("tabsInfo");
                JSONObject tabs = (JSONObject) tabsInfo.getJSONArray("tabs").get(0);

                WeiBoExportVO weiBoExportVO = new WeiBoExportVO();
                weiBoExportVO.setScreen_name( screen_name );
                weiBoExportVO.setContainerId(tabs.getString("containerid"));
                weiBoExportVO.setFollowers_count( following_count );
                return weiBoExportVO;
            } else {
                log.error(url + " 请求的状态码错误" + success);
                throw new RemoteAccessException("根据微博id请求异常");
            }

        } catch (RemoteAccessException e) {
            throw new RemoteAccessException(e.getLocalizedMessage());
        }  catch (HttpHostConnectException e) {
            log.error( "连接异常 url:" + url);
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("未知异常url：" + url + "错误日志" + e.getLocalizedMessage());
        }
        return null;
    }



}
