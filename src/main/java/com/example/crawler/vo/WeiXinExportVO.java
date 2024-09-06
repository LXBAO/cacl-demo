package com.example.crawler.vo;

public class WeiXinExportVO {
    private int read_num;//阅读数
    private int old_like_num;//点赞数
    private int like_num;//在看数

    private String accName;
    private String account;

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getRead_num() {
        return read_num;
    }

    public void setRead_num(int read_num) {
        this.read_num = read_num;
    }

    public int getOld_like_num() {
        return old_like_num;
    }

    public void setOld_like_num(int old_like_num) {
        this.old_like_num = old_like_num;
    }

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }
}
