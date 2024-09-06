package com.example.crawler.vo;

/**
 * @author lx
 * @data 2022/11/14 11:02
 */
public class WeiXinMappingVO {

    private String biz;

    private String accName;

    private Double offset;

    public Double getOffset() {
        return offset;
    }

    public void setOffset(Double offset) {
        this.offset = offset;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }
}
