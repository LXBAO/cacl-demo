package com.example.crawler.service;

import com.alibaba.fastjson.JSONObject;
import com.example.crawler.vo.DouYinExportVO;
import com.example.crawler.vo.TouTiaoExportVO;

import java.util.List;


public interface ITouTiaoDetailService {
     List<JSONObject> executor(TouTiaoExportVO touTiaoExportVO, String id,String sessionId);
}
