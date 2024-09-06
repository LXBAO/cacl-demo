package com.example.crawler.vo;

public class TouTiaoExportVO {
    private int read_count;//阅读数
    private String follower_count;//粉丝数
    private int aweme_count;//作品数
    private int comment_count; //评论

    public int getRead_count() {
        return read_count;
    }

    public void setRead_count(int read_count) {
        this.read_count = read_count;
    }

    public String getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(String follower_count) {
        this.follower_count = follower_count;
    }

    public int getAweme_count() {
        return aweme_count;
    }

    public void setAweme_count(int aweme_count) {
        this.aweme_count = aweme_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }
}
