package com.jiangxin.httplibrary;

/**
 * Created by jiangxin on 2016/8/11.
 */
public interface HttpCallback {

    /*
    * 请求成功后的接口回调
    * */
    void success(String result,int requestCode);
}
