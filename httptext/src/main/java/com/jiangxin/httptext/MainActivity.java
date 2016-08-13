package com.jiangxin.httptext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jiangxin.httplibrary.HttpCallback;
import com.jiangxin.httplibrary.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements HttpCallback{

    private String value = "{\"delta\":false,\"package\":\"com.shunyou.gifthelper\",\"appkey\":\"54d32548fd98c5fd57000a56\",\"sdk_version\":\"2.5.0.20141210\",\"type\":\"update\",\"channel\":\"1688wan\",\"old_md5\":\"9be6f645823329d5bb70d76008edd6a3\",\"proto_ver\":\"1.4\",\"idmd5\":\"528447f4ffb4e4824a2fd1d1f0cd62\",\"version_code\":\"10\"}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void click(View view){
        Map<String,Object> map=new HashMap<>();
        map.put("content",value);
        HttpUtils.load("http://au.umeng.com/api/check_app_update").post(map).callback(this,1);
    }

    @Override
    public void success(String result, int requestCode) {
        Log.d("androidxx",result);
    }
}
