package com.example.crawler.service;

import com.example.crawler.vo.WeiXinMappingVO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lx
 * @data 2022/11/14 11:22
 */
@Component
@ConfigurationProperties(prefix = "weixin-configs")
public class ParamConfiguration{
    private Map<String, WeiXinMappingVO> maps =new HashMap<String,WeiXinMappingVO>();

    public Map<String, WeiXinMappingVO> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, WeiXinMappingVO> maps) {
        this.maps = maps;
    }

    public WeiXinMappingVO  getWeiXinMappingVO(String key) {
       return maps.get(key);
    }
}
