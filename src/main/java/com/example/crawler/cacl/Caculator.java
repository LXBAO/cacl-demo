package com.example.crawler.cacl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.crawler.vo.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Caculator {

    String aa;
    public DouYinExportVO caculDouYin(List<JSONObject> list,DouYinExportVO douYinExportVO){
        int total_comment_count = 0; //评论
        int total_share_count = 0;//转发
        for(JSONObject jsonObject :list){
            Integer comment_count = jsonObject.getInteger("comment_count");
            Integer share_count = jsonObject.getInteger("share_count");
            total_comment_count+=comment_count;
            total_share_count+=share_count;
        }
        douYinExportVO.setComment_count(total_comment_count);
        douYinExportVO.setShare_count(total_share_count);

        System.out.println("名称："+douYinExportVO.getAccName()+"抖音号："+douYinExportVO.getAccount()+"关注数："+douYinExportVO.getFollowing_count()+ "点赞数："+douYinExportVO.getFavoriting_count()+
                "粉丝数："+douYinExportVO.getFollower_count()+"作品数："+ douYinExportVO.getAweme_count()+
                "转发数："+total_share_count+"评论数："+total_comment_count);
        return douYinExportVO;
    }

    public WeiBoExportVO caculweiBo(JSONArray jsonArray, WeiBoExportVO weiBoExportVO){
        // '转发数'
        int total_reposts_count = 0;
        //评论数
        int total_comments_count = 0;
        //点赞数
        int total_attitudes_count = 0;
        //原创微博数量
        int original_size = 0;
        //  原创转发数'
        int total_original_count = 0;
        //原创评论数
        int total_original_comments_count = 0;
        try{
            for(Object obj :jsonArray){
                JSONObject jsonObject = (JSONObject) obj;
                int  reposts_count  = jsonObject.getInteger("reposts_count");
                int  comments_count  = jsonObject.getInteger("comments_count");
                int  attitudes_count  = jsonObject.getInteger("attitudes_count");
                total_reposts_count+=reposts_count;
                total_comments_count+=comments_count;
                total_attitudes_count+=attitudes_count;
                if(jsonObject.getJSONObject("retweeted_status")==null){
                    total_original_count +=reposts_count;
                    total_original_comments_count+=comments_count;
                    original_size++;
                }
            }
            weiBoExportVO.setReposts_count(total_reposts_count);
            weiBoExportVO.setComments_count(total_comments_count);
            weiBoExportVO.setOriginal_comments_count(total_original_comments_count);
            weiBoExportVO.setOriginal_count(total_original_count);
            weiBoExportVO.setAttitudes_count(total_attitudes_count);
            weiBoExportVO.setOriginal_size(original_size);
            return weiBoExportVO;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;

    }


    public BilBilExportVO caculBilBil(List<Object> list , BilBilExportVO bilBilExportVO){
        int count = 0;
        try{
            for(Object t :list) {
                JSONObject jsonObject = (JSONObject) t;
                int comment_count = jsonObject.getInteger("comment");
                count += comment_count;
            }
            bilBilExportVO.setComment_count(count);
            return bilBilExportVO;
        }catch (Exception e){

        }
        return null;
    }


    public TouTiaoExportVO caculToutiao(List<JSONObject> list, TouTiaoExportVO touTiaoExportVO){
        int total_read_count = 0;
        int total_comment_count = 0;
        try{
            for(JSONObject jsonObject :list) {
                int read_count = jsonObject.getInteger("total_read_count");
                int comment_count = jsonObject.getInteger("comment_count");
                total_read_count += read_count;
                total_comment_count += comment_count;
            }
            touTiaoExportVO.setAweme_count(list.size());
            touTiaoExportVO.setComment_count(total_comment_count);
            touTiaoExportVO.setRead_count(total_read_count);
            return touTiaoExportVO;
        }catch (Exception e){
            System.out.println(e);
        }
        return null;

    }

    public WeiXinExportVO caculWeiXin(List<JSONObject> list) {
        int read_num = 0; //阅读数
        int old_like_num = 0;//点赞数
        int like_num = 0;//在看数
        try {
            for (JSONObject jsonObject : list) {
                JSONObject appmsgstat = jsonObject.getJSONObject("appmsgstat");
                read_num += Integer.parseInt(appmsgstat.getString("read_num"));
                old_like_num += Integer.parseInt(appmsgstat.getString("old_like_num"));
                like_num += Integer.parseInt(appmsgstat.getString("like_num"));
            }
            WeiXinExportVO exportVO = new WeiXinExportVO();
            exportVO.setLike_num(like_num);
            exportVO.setOld_like_num(old_like_num);
            exportVO.setRead_num(read_num);
            return exportVO;
        } catch (Exception e) {
            throw new RuntimeException(e);

        }
    }
}
