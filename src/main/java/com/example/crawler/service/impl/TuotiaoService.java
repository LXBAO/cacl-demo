package com.example.crawler.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.Constants;
import com.example.crawler.service.IDouYinService;
import com.example.crawler.service.ITouTiaoService;
import com.example.crawler.vo.DouYinExportVO;
import com.example.crawler.vo.TouTiaoExportVO;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("tuotiaoService")
public class TuotiaoService implements ITouTiaoService {
    Logger log = LoggerFactory.getLogger(TuotiaoService.class);
    String token = "MS4wLjABAAAAXEILF_u-f7WdQkPIzRSeSNJnroZ3F9B9klMIbPDpFsBR7NWiSaJ4Fhi26Z81-f7p";
    Map<String,String> map  = new HashMap<>();
    @Override
    @Retryable(
            value = RemoteAccessException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 5000L, multiplier = 1))
    public TouTiaoExportVO executor(String media_id) {
        CloseableHttpClient client = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String url = Constants.TOUTIAO_ARTICLE_URL + token;
        try {
            HttpGet httpget = new HttpGet(url);
            httpget.setHeader("User-Agent", Constants.USER_AGENT);

            response = client.execute(httpget);
            int success = response.getStatusLine().getStatusCode();
            if (Constants.SUCCESS == success) {
                JSONObject ret = JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                JSONObject data = ret.getJSONObject("data");
                JSONArray dataList = data.getJSONArray("data");
                for(Object info : dataList){
                    JSONObject user = (JSONObject) info;
                    map.put(user.getString("media_id"),user.getString("fans"));
                }

                TouTiaoExportVO touTiaoExportVO= new TouTiaoExportVO();
                touTiaoExportVO.setFollower_count(map.get(media_id));
                return touTiaoExportVO;
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
