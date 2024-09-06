package com.example.crawler.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.service.IWeixinDetailService;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
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

@Service("weiXinDetailService")
public class WeiXinDetailService implements IWeixinDetailService {
    Logger log = LoggerFactory.getLogger(WeiXinDetailService.class);



    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public JSONObject executor(String url,  String key) {

        try {
            String mid = url.split("&")[1].split("=")[1];
            String idx = url.split("&")[2].split("=")[1];
            String sn = url.split("&")[3].split("=")[1];
            String _biz = url.split("&")[0].split("_biz=")[1];
            Thread.sleep(4000);
            /**爬虫最好使用代理ip
             * 以下是HttpClient使用代理ip
             *HttpHost post = new HttpHost(ip, port);
             *  //超时时间单位为毫秒
             * RequestConfig defaultRequestConfig = RequestConfig.custom()
             * .setConnectTimeout(CONNECTION_TIME_OUT).setSocketTimeout(CONNECTION_TIME_OUT)
             * .setProxy(post).build();
             * client = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
             */
            CloseableHttpResponse response = null;
            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost post = new HttpPost(Constants.WEIXIN_ARTICLE_DETAIL_URL);

            /**
             * 包装成一个Entity对象
             */
            Map<String, Object> params = new HashMap<>();
            params.put("mid", mid);
            params.put("idx", idx);
            params.put("sn", sn);
            params.put("is_only_read", "1");
            params.put("is_temp_url", "0");
            params.put("appmsg_type", "9");
            params.put("reward_uin_count", "0");
            params.put("__biz", _biz);
            params.put("key", key);
            params.put("uin", Constants.WEIXIN_UIN);

            List<NameValuePair> nameValuePairList = new ArrayList<>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                nameValuePairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            StringEntity entity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            /**
             * 设置请求的内容
             */
            post.setEntity(entity);
            post.setHeader(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; UTF-8"));
            post.setHeader(new BasicHeader("Origin", "https://mp.weixin.qq.com"));
            post.setHeader(new BasicHeader("Host", "mp.weixin.qq.com"));
            post.setHeader(new BasicHeader("User-Agent", Constants.USER_AGENT_WEIXIN));
            post.addHeader(new BasicHeader("Cookie","rewardsn=; wxtokenkey=777; wxuin=61194251; devicetype=Windows10x64; version=6307062c; lang=zh_CN; appmsg_token=1191_WjedFvAsBKU2iRbkuHi8vK5ofzz3L6wUA3MI5D0yZvfyNQJe8wizac0vXfg~; pass_ticket=ndLnENSfB9CCeUfPRb9HcwAxIPLfvURKxWQcHOJd48jik0uYNV814u4NiD94yP0k; wap_sid2=CIuAlx0SigF5X0hJXzUzRFU3WE82cTNIX3BEMTBZNTM1b3RraktsVjBrdU9YaElqdWRmdUQwYUg2SFd4UjVGUy1WZUU5ZVdlanNrWU1nMmdOYTNxUFRYcF9RaTMwakNGQTNXUDJQTFNvclRleTdtNWRkb0NxWHI1NGZ5TmZ3UkhGSXpuRUFDSjhJSFlVU0FBQX4wiZ2+mwY4DUAB"));
            response = client.execute(post);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == Constants.SUCCESS) {
                String result = EntityUtils.toString(response.getEntity(), "UTF-8");
                JSONObject jsonObject = JSONObject.parseObject(result);
                return jsonObject;
            } else {
                log.error(url + "+:请求的状态码错误" + statusCode);
                throw new RemoteAccessException("根据抖音id请求用户异常");
            }

        } catch (RemoteAccessException e) {
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (HttpHostConnectException e) {
            log.error("连接异常 url:" + url);
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (Exception e) {
            log.error("未知异常url：" + url + "错误日志" + e.getLocalizedMessage());

        }
        return null;
    }

}
