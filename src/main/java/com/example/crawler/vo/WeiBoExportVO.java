package com.example.crawler.vo;

/**
 * @author lx
 * @data 2022/11/3 11:15
 */
public class WeiBoExportVO {

    // '转发数'
    private int reposts_count;

    private String containerId;
    //评论数
    private int comments_count;
    //点赞数
    private int attitudes_count;
    //粉丝
    private String followers_count;
    //微博昵称
    private String screen_name;
    //总发微博数量
    private int total_size;

    //原创微博数量
    private int original_size;
    //  原创转发数'
    private int original_count;

    //原创评论数
    private int original_comments_count;

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    public int getReposts_count() {
        return reposts_count;
    }

    public void setReposts_count(int reposts_count) {
        this.reposts_count = reposts_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getAttitudes_count() {
        return attitudes_count;
    }

    public void setAttitudes_count(int attitudes_count) {
        this.attitudes_count = attitudes_count;
    }

    public String getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(String followers_count) {
        this.followers_count = followers_count;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public int getTotal_size() {
        return total_size;
    }

    public void setTotal_size(int total_size) {
        this.total_size = total_size;
    }

    public int getOriginal_size() {
        return original_size;
    }

    public void setOriginal_size(int original_size) {
        this.original_size = original_size;
    }

    public int getOriginal_count() {
        return original_count;
    }

    public void setOriginal_count(int original_count) {
        this.original_count = original_count;
    }

    public int getOriginal_comments_count() {
        return original_comments_count;
    }

    public void setOriginal_comments_count(int original_comments_count) {
        this.original_comments_count = original_comments_count;
    }
}
