package com.example.crawler.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.vo.DouYinExportVO;
import com.example.crawler.vo.Page;
import com.example.crawler.vo.Params;
import com.example.crawler.vo.WeiBoExportVO;

import java.util.List;


public interface IWeiBoDetailService {
     JSONArray executor(WeiBoExportVO weiBoExportVO, Page<Params> page);
}
