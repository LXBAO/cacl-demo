package com.example.crawler.service;

import com.alibaba.fastjson.JSONObject;
import com.example.crawler.vo.DouYinExportVO;

import java.util.List;


public interface IDouYinDetailService {
     List<JSONObject> executor(DouYinExportVO douYinExportVO, String key);
}
