package com.example.crawler;

import com.example.crawler.manager.CrawlerManager;
import com.example.crawler.service.IWeixinDetailService;
import com.example.crawler.service.Properties;
import com.example.crawler.vo.Page;
import com.example.crawler.vo.Params;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CrawlerController {
    @Autowired
    CrawlerManager crawlerManager;

    @Autowired
    IWeixinDetailService weiXinDetailService;

    @Autowired
    private Environment environment;
    @Autowired
    Properties properties;

    @GetMapping(value = "/config")
    public void config() throws Exception {
       List<String>  s1 = environment.getProperty("names.list",List.class);
       System.out.println(properties.user.name);
    }

    @GetMapping(value = "/weiXinExecutor")
    public void weiXinExecutor(@RequestParam("account") String account,
                               @RequestParam("keys") List<String> keys,
                               @RequestParam("startTime") Long startTime,
                               @RequestParam("endTime") Long endTime) throws Exception {
        crawlerManager.weiXinExecutor(account,keys,startTime,endTime);

    }

    @GetMapping(value = "/douYinExecutor")
    public void douYinExecutor(@RequestParam("key") String key) throws Exception {
          crawlerManager.douYinExecutor(key);

    }

    @PostMapping(value = "/weiBoExecutor")
    public void weiBoExecutor(@RequestBody Page<Params> page) throws Exception {

       crawlerManager.weiBoExecutor( page);
    }


    @GetMapping(value = "/touTiaoExecutor")
    public void toutiaoExecutor(@RequestParam("uid") String uid,

                                @RequestParam("sessionId") String sessionId) throws Exception {

        crawlerManager.touTiaoExecutor(uid,sessionId);
    }



    @PostMapping(value = "/bilBilExecutor")
    public void bilBilExecutor(@RequestBody Page<Params> page) throws Exception {

        crawlerManager.bilBilExecutor(page);
    }


    @GetMapping(value = "/ksExecutor")
    public void ksExecutor(@RequestParam("userId") String userId) throws Exception {

        crawlerManager.ksExecutor(userId);
    }


}
