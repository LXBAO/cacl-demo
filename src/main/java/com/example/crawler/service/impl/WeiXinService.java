package com.example.crawler.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;

import com.example.crawler.service.ParamConfiguration;
import com.example.crawler.vo.WeiXinMappingVO;

import com.example.crawler.service.IWeiXinService;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Service("weiXinService")
public class WeiXinService implements IWeiXinService {
    Logger log = LoggerFactory.getLogger(WeiXinService.class);

    ThreadLocal<List<String>> threadLocal = new ThreadLocal<>();
    ThreadLocal<Integer> offset = new ThreadLocal<>();

    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public List<String> executor(String account,String biz,List<String> keys,Long startTime,Long endTime,int boffset) {
        if (threadLocal.get() == null) {
            threadLocal.set(new ArrayList<>());
            offset.set(boffset);
        }
        startTime= startTime/1000;
        endTime = endTime/1000;
        log.debug("开始执行爬取公众号：" + account);
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url = "";
        int index=0;
        boolean bool = false;
        try {
            while (true) {
               url = Constants.WEIXIN_ARTICLE_URL +
                        "action=getmsg&" +
                        "__biz=" + biz +
                        "&f=json&offset=" + offset.get() +
                        "&count=10" +
                        "&uin=" + Constants.WEIXIN_UIN +
                        "&key=" + keys.get(index);

                HttpGet httpget = new HttpGet(url);
                httpget.setHeader("User-Agent", Constants.USER_AGENT_WEIXIN);
                httpget.addHeader(new BasicHeader("Cookie","rewardsn=; wxtokenkey=777; wxuin=1235488408; devicetype=Windows10x64; version=6307062c; lang=zh_CN; appmsg_token=1191_0IYTlQGJaLibtLRbKEoyXHQtWT7BYsw-VgAfDtAs0xY94VwPF_3CW9fgce2PzykIIHppMZkoqq7CXK0T; pass_ticket=1w0ceEdxCt2D+JI6hP8T+FUmh+CoCrkLy4xujqsMpkIQ4XEaXsPFHQ4axlw9kils; wap_sid2=CJidkM0EEp4BeV9IQkZOekN2Vl9FcG1qX0ZLUWN1dm9JcXhNaXpzVEgyUlNkanNRYk1fN1Q2b1c5QVA0S3lrbmlEbFNrcnMwWTlJOXlXM09CaG9qNGR6Vl9kTXQ2ejc5dWtxY3M2V0VraHZlcjMtdE5TQ09DVTJGekZabjJKSkotMlk3TExYbnJtSFBnQVpiTDFXbGxqVy14QXVBdE1xY1cyRkVnQUEwh+rHmwY4DUAB"));
                response = client.execute(httpget);
                int success = response.getStatusLine().getStatusCode();
                if (Constants.SUCCESS != success) {
                    log.debug(url + "+:请求的状态码错误" + success);
                    throw new RemoteAccessException("获取微信所有文章列表状态码错误："+success);
                }
                // 解析数据返回内容
                JSONObject jsonObject = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                String general_msg_list = jsonObject.getString("general_msg_list");
                if (StringUtils.isEmpty(general_msg_list)) {
                    log.debug("访问秘钥已过期");
                    break;
                }
                JSONObject jsonObject1 = JSONObject.parseObject(general_msg_list);
                JSONArray list = jsonObject1.getJSONArray("list");
                if (list.isEmpty()) {
                    log.debug("该公众号没有更多文章了");
                    break;
                }
                index++;
                if(index >=keys.size()){
                    index = 0;
                }
                for (Object o : list) {
                    String toJSONString = JSONObject.toJSONString(o);
                    JSONObject jsonObject2 = JSONObject.parseObject(toJSONString);
                    JSONObject app_msg_ext_info = jsonObject2.getJSONObject("app_msg_ext_info");
                    //有的没有新闻，是公告
                    if(app_msg_ext_info==null){
                        continue;
                    }

                    Long dateTime =  jsonObject2.getJSONObject("comm_msg_info").getLong("datetime");
                    if(startTime >=dateTime && endTime <=dateTime){
                        String content_url = app_msg_ext_info.getString("content_url");
                        threadLocal.get().add(content_url);
                    }
                    if(endTime> dateTime){
                        bool = true;
                        break;
                    }

                }
                if(bool){
                    break;
                }
                offset.set(offset.get()+Constants.COUNT);
                Thread.sleep(3000);
            }
        }
        catch (RemoteAccessException e) {
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (HttpHostConnectException e) {
            log.debug("连接异常 url:" + url);
            throw new RemoteAccessException(e.getLocalizedMessage());
        } catch (Exception e) {
            log.debug("未知异常url：" +url + "错误日志" + e.getLocalizedMessage());
        }
        List<String> list = new ArrayList<>();
        list.addAll(threadLocal.get());
        threadLocal.remove();
        return list;
    }


}



