package com.example.crawler.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.service.IKsCommentService;
import com.example.crawler.vo.KsExportVO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lx
 * @data 2022/11/10 12:46
 */
@Service("ksCommentService")
public class KsCommentService implements IKsCommentService {
    Logger log = LoggerFactory.getLogger(DouYinService.class);


    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public int executor(String id) {

        String url = "https://www.kuaishou.com/graphql";
        try {

            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = null;

            Map<String, Object> params = new HashMap<>();
            params.put("operationName", "commentListQuery");
            params.put("query", "query commentListQuery($photoId: String, $pcursor: String) {   visionCommentList(photoId: $photoId, pcursor: $pcursor) {     commentCount     pcursor     rootComments {       commentId       authorId       authorName       content       headurl       timestamp       likedCount       realLikedCount       liked       status       subCommentCount       subCommentsPcursor       subComments {         commentId         authorId         authorName         content         headurl         timestamp         likedCount         realLikedCount         liked         status         replyToUserName         replyTo         __typename       }       __typename     }     __typename   } } ");
            Map<String, Object> params2 = new HashMap<>();
            params2.put("photoId", id);

            params2.put("pcursor", "");
            params.put("variables", params2);
            JSONObject jsonObject = new JSONObject();
            jsonObject.putAll(params);
            String json = jsonObject.toJSONString();
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader(new BasicHeader("Cookie", "userId=3111850080;kuaishou.server.web_st=ChZrdWFpc2hvdS5zZXJ2ZXIud2ViLnN0EqABiAwoeGjSVaintFDyOLsiw8AsWaCx7ME32j4VrCNbLb6zqvIte7FE9gwcMWlr-UVTUShNV143aqfGuX3HZcxeAIPW_JmfOSGHzCWceSFyFkpPeWPPwcgWfxerN7WBcNg8vbHDP9CApa-pwQ4iqVfDZd3QosT7FYQFxAlGgLV7dw7WwgZXdUbt8tMm3a_X1oMQgpHMNfJ1L23ye-cMvy_quRoSg3ZkWJHNsQvvc8vsvUksYr6BIiAMJlLr6Xju5ElKJqA_5CggU587N6glDWcqHgMUGvtU3CgFMAE;   kuaishou.server.web_ph=e55c3a748240c57aefd3ae6e942bc33ee819;  did=web_a40d17c9548b3b1a6e5ab8ab6f9ca6df"));
            httppost.setHeader(new BasicHeader("User-Agent", Constants.USER_AGENT));
            StringEntity entity = new StringEntity(json, "UTF-8");
            entity.setContentType("application/json");

            httppost.setEntity(entity);
            response = client.execute(httppost);

            int success = response.getStatusLine().getStatusCode();
            // 解析数据返回内容
            if (Constants.SUCCESS == success) {
                JSONObject jsonObject2 = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                JSONObject data = jsonObject2.getJSONObject("data");
                JSONObject visionProfilePhotoList = data.getJSONObject("visionCommentList");
                int commentCount = visionProfilePhotoList.getInteger("commentCount");
                return commentCount;
            } else {
                log.error(url + "+:请求的状态码错误" + success);
                throw new RemoteAccessException("根据快手视频id请求用户异常");
            }

        } catch (RemoteAccessException e) {
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (HttpHostConnectException e) {
            log.error("连接异常url:" + url);
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("未知异常url：" + url + "错误日志" + e.getLocalizedMessage());
        }

        return 0;
    }


}
