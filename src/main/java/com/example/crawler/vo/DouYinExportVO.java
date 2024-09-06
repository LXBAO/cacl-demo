package com.example.crawler.vo;

public class DouYinExportVO {
    private int following_count;//关注数
    private int favoriting_count;//点赞数
    private int follower_count;//粉丝数
    private int aweme_count;//作品数
    private int comment_count = 0; //评论
    private int share_count = 0;//转发
    //公众号名称
    private String accName;
    //公众号id
    private String account;
    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

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

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public int getFollowing_count() {
        return following_count;
    }

    public void setFollowing_count(int following_count) {
        this.following_count = following_count;
    }

    public int getFavoriting_count() {
        return favoriting_count;
    }

    public void setFavoriting_count(int favoriting_count) {
        this.favoriting_count = favoriting_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getAweme_count() {
        return aweme_count;
    }

    public void setAweme_count(int aweme_count) {
        this.aweme_count = aweme_count;
    }
}
