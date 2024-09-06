package com.example.crawler.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.service.IDouYinService;
import com.example.crawler.vo.DouYinExportVO;
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

@Service("douYinService")
public class DouYinService implements IDouYinService {
    Logger log = LoggerFactory.getLogger(DouYinService.class);

    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public DouYinExportVO executor(String userIdKey) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url = Constants.DOUYIN_ARTICLE_URL + userIdKey;
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent", Constants.USER_AGENT);

            response = client.execute(httpget);
            int success = response.getStatusLine().getStatusCode();
            if (Constants.SUCCESS == success) {
                JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                JSONObject jsonObject2 = jsonObject.getJSONObject("user_info");
                if (jsonObject2 == null) {

                    throw new RuntimeException("根据抖音id没有获取到用户信息");
                }
                int following_count = jsonObject2.getInteger("following_count");
                int mplatform_followers_count = jsonObject2.getInteger("mplatform_followers_count");
                int total_favorited = jsonObject2.getInteger("total_favorited");
                int aweme_count = jsonObject2.getInteger("aweme_count");

                String nickname = jsonObject2.getString("nickname");
                String unique_id = jsonObject2.getString("unique_id");

                DouYinExportVO douYinExportVO = new DouYinExportVO();
                douYinExportVO.setAweme_count(aweme_count);//作品数
                douYinExportVO.setFavoriting_count(mplatform_followers_count);//粉丝数
                douYinExportVO.setFollowing_count(following_count);//关注数
                douYinExportVO.setFollower_count(total_favorited);//点赞数
                douYinExportVO.setAccName(nickname);
                douYinExportVO.setAccount(unique_id);
                return douYinExportVO;
            } else {
                log.error(url + " 请求的状态码错误" + success);
                throw new RemoteAccessException("根据抖音id请求用户异常");
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
