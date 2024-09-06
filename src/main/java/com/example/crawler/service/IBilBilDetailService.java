package com.example.crawler.service;

import com.example.crawler.vo.BilBilExportVO;
import com.example.crawler.vo.Page;
import com.example.crawler.vo.Params;

import java.util.List;


public interface IBilBilDetailService {
     List<Object> executor(BilBilExportVO bilBilExportVO, Page<Params> page);
}
