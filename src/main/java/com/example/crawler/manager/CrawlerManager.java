package com.example.crawler.manager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.cacl.Caculator;
import com.example.crawler.service.*;
import com.example.crawler.service.impl.BilBilDetailService;
import com.example.crawler.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Component("crawlerManager")
public class CrawlerManager {
    @Autowired
    IWeiXinService weiXinService;
    @Autowired
    IWeixinDetailService weiXinDetailService;

    @Autowired
    IDouYinService douYinService;

    @Autowired
    IDouYinDetailService douYinDetailService;

    @Autowired
    IWeiBoService weiBoService;

    @Autowired
    IWeiBoDetailService weiBoDetailService;

    @Autowired
    ITouTiaoDetailService touTiaoDetailService;

    @Autowired
    ITouTiaoService tuotiaoService;

    @Autowired
    IBilBilService bilBilService;
    @Autowired
    BilBilDetailService bilBilDetailService;

    @Autowired
    IKSService kSService;
    @Autowired
    IKsDetailService ksDetailService;
    @Autowired
    IKsCommentService ksCommentService;
    @Autowired
    Caculator caculator;
    @Autowired
    ParamConfiguration paramConfiguration;
    public WeiXinExportVO weiXinExecutor(String account,List<String> keys,Long startTime,Long endTime) throws Exception {
        WeiXinMappingVO weiXinMappingVO = paramConfiguration.getWeiXinMappingVO(account);

        //换个g公众号需要修改biz
        String biz = weiXinMappingVO.getBiz();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int day = calcDay(  startTime,timestamp.getTime()) ;
        int offset = calcOffset(day,weiXinMappingVO.getOffset());

        List<String> urlList = weiXinService.executor(account,biz,keys,  startTime,  endTime,offset);
        List<JSONObject> jsonObjects = new ArrayList<>();

        int keyIndex = 0;
        for(String url: urlList){

            JSONObject jsonObject= weiXinDetailService.executor(url,keys.get(keyIndex));
            keyIndex++;
            if(keyIndex >= keys.size()){
                keyIndex = 0;
            }

            jsonObjects.add(jsonObject);
        }
        WeiXinExportVO weiXinExportVO = caculator.caculWeiXin(jsonObjects);
        weiXinExportVO.setAccName(weiXinMappingVO.getAccName());
        weiXinExportVO.setAccount(account);
        System.out.println("公众号名称："+weiXinMappingVO.getAccName()+"公众号Id"+account+"阅读数："+weiXinExportVO.getRead_num()+
                "点赞数："+weiXinExportVO.getOld_like_num()+"在看数："+weiXinExportVO.getLike_num());
        return weiXinExportVO;
    }

    //计算天
    public int calcDay(Long starTime,Long endTime){
        Long num=(endTime-starTime)/(24*60*60*1000);//时间戳相差的毫秒数
        return num.intValue();
    }

    public int calcOffset(int day,Double offset){
        Double  num= day*offset;
        return num.intValue();
    }
    public DouYinExportVO douYinExecutor(String key) throws Exception {

        DouYinExportVO douYinExportVO = douYinService.executor(key);
        if(douYinExportVO!=null){
            List<JSONObject>  douYinExportDetailVOS = douYinDetailService.executor(douYinExportVO,key);
            douYinExportVO = caculator.caculDouYin(douYinExportDetailVOS,douYinExportVO);
        }
        return douYinExportVO;

    }

    public WeiBoExportVO weiBoExecutor(Page<Params> page) throws Exception {

        WeiBoExportVO weiBoExportVO = weiBoService.executor(page.getT().getId());
        if(weiBoExportVO!=null){
            JSONArray jsonObjects = weiBoDetailService.executor(weiBoExportVO,page);
            weiBoExportVO = caculator.caculweiBo(jsonObjects,weiBoExportVO);
        }
        return weiBoExportVO;
    }

    public TouTiaoExportVO touTiaoExecutor(String key,String sessionId) throws Exception {
        TouTiaoExportVO touTiaoExportVO=  tuotiaoService.executor(key);
        if(touTiaoExportVO!=null){
            List<JSONObject> list = touTiaoDetailService.executor(touTiaoExportVO,key,sessionId);
            caculator.caculToutiao(list,touTiaoExportVO);
        }
        return touTiaoExportVO;

    }

    public BilBilExportVO bilBilExecutor(Page<Params> page) throws Exception {
        BilBilExportVO bilBilExportVO= new  BilBilExportVO();
        bilBilService.executor(page.getT().getId(),page.getT().getToken(),0,bilBilExportVO);
        List<Object>list =  bilBilDetailService.executor(bilBilExportVO,page);
        caculator.caculBilBil(list,bilBilExportVO);
        return bilBilExportVO;

    }


    public KsExportVO ksExecutor(String key) throws Exception {
        KsExportVO ksExportVO = kSService.executor(key);
        List<JSONObject>list =  ksDetailService.executor(ksExportVO,key);
        int totalRealLikeCount = 0;
        int totalViewCount = 0;
        int totalCommentCount = 0;
        for(JSONObject obj: list){
            String id =  obj.getString("id");
            int commentCount = ksCommentService.executor(id);
            totalCommentCount+=commentCount;
            int realLikeCount = obj.getInteger("realLikeCount");
            totalRealLikeCount+= realLikeCount;
            int viewCount = obj.getInteger("viewCount");
            totalViewCount+=viewCount;
        }
        ksExportVO.setComment_count(totalCommentCount);
        ksExportVO.setViewCount(totalViewCount);
        ksExportVO.setFavoriting_count(totalRealLikeCount);
        return ksExportVO;

    }
}
