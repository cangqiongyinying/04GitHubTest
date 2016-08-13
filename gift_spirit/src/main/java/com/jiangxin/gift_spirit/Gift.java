package com.jiangxin.gift_spirit;

/**
 * Created by my on 2016/8/5.
 */
public class Gift {
    public String picUrl;
    public String title;
    public String giftType;
    public String count;
    public String time;

    public Gift(String title,String giftType,String count,String time,String picUrl){
        this.title=title;
        this.giftType=giftType;
        this.count=count;
        this.time=time;
        this.picUrl=picUrl;
    }
}
