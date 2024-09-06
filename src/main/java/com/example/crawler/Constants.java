package com.example.crawler;

public class Constants {
    public static final String USER_AGENT=" Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36";
    public static final String USER_AGENT_WEIXIN = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36 NetType/WIFI MicroMessenger/7.0.20.1781(0x6700143B) WindowsWechat(0x6307062c)";
    public static final Integer SUCCESS=200;

    public static final Integer COUNT=10;
    public static final String WEIXIN_UIN = "NjExOTQyNTE=";
    public static final String WEIXIN_ARTICLE_URL="https://mp.weixin.qq.com/mp/profile_ext?";

    public static final String WEIXIN_ARTICLE_DETAIL_URL="https://mp.weixin.qq.com/mp/getappmsgext";

    public static final String DOUYIN_ARTICLE_DETAIL_URL = "https://www.iesdouyin.com/web/api/v2/aweme/post/?sec_uid={0}&count=30&max_cursor={1}&aid=1128";

    public static final String DOUYIN_ARTICLE_URL="https://www.iesdouyin.com/web/api/v2/user/info/?sec_uid=";


    public static final String WEIBO_ARTICLE_URL="https://m.weibo.cn/api/container/getIndex?type=uid&value=";

    public static final String WEIBO_ARTICLE_DETAIL_URL = "https://weibo.com/ajax/statuses/mymblog?uid={0}&page={1}&feature=0";


    public static final String TOUTIAO_ARTICLE_URL="https://www.toutiao.com/api/pc/user/following?cursor=0&count=20&token=";

    public static final String TOUTIAO_ARTICLE_DETAIL_URL = "https://www.toutiao.com/pgc/ma/?page_type=1&max_behot_time={0}&uid={1}&media_id={2}&count=10&as={3}&cp={4}";


    public static final String BILBIL_ARTICLE_URL="https://api.bilibili.com/x/space/upstat?mid=";
    public static final String BILBIL2_ARTICLE_URL="https://api.bilibili.com/x/relation/stat?vmid=";
    public static final String BILBIL_ARTICLE_DETAIL_URL = "https://api.bilibili.com/x/space/arc/search?mid={0}&ps=30&tid=0&pn={1}&keyword=&order=pubdate&order_avoided=true";
    public static final Integer BILBIL_PAGE_SIZE = 30;
}
