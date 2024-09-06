package com.example.crawler.service;



import java.util.List;

public interface IWeiXinService {
      List<String> executor(String account,String biz,List<String> keys,Long startTime,Long endTime,int offset);

}
