package com.example.crawler.service;

import com.alibaba.fastjson.JSONObject;
import com.example.crawler.vo.BilBilExportVO;
import com.example.crawler.vo.KsExportVO;

import java.util.List;


public interface IKsDetailService {
     List<JSONObject> executor(KsExportVO ksExportVO, String userId);
}
