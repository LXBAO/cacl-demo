package com.example.crawler.service;


import com.example.crawler.vo.BilBilExportVO;

public interface IBilBilService {
      void executor(String userIdKey, String sessData, int index,BilBilExportVO bilBilExportVO);

}
