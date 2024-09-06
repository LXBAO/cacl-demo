package com.example.crawler.vo;

public class BilBilExportVO {
    private int follower_count;//粉丝数
    private int favoriting_count;//点赞数
    private int aweme_count;//作品数
    private int comment_count = 0; //评论

    public int getComment_count() {
        return comment_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
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
