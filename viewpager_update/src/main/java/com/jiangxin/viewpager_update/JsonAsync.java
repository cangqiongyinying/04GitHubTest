package com.jiangxin.viewpager_update;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by my on 2016/7/30.
 */

public class JsonAsync extends AsyncTask<String,Void,UpdateMessage>{
    public onJsonSuccessListener mListener = null;
    public interface onJsonSuccessListener{
        public void onSuccessed(UpdateMessage update);
    }

    public void setOnJsonSuccessListener(onJsonSuccessListener listener){
        mListener = listener;
    }

    @Override
    protected UpdateMessage doInBackground(String... params) {
        UpdateMessage updateMessage = new UpdateMessage();
        String jsonStr = getTextFromUrl();
        if(TextUtils.isEmpty(jsonStr)){
            return updateMessage;
        }
        Gson gson = new Gson();
        Update update=gson.fromJson(jsonStr,Update.class);
        updateMessage.proto_ver=update.proto_ver;
        Log.v("jsonAsync",updateMessage.proto_ver);
        updateMessage.update_log=update.update_log;
        updateMessage.version=update.version;
        return updateMessage;
    }

    @Override
    protected void onPostExecute(UpdateMessage result) {
        if(mListener != null){
            mListener.onSuccessed(result);
        }
        super.onPostExecute(result);
    }

    public String getTextFromUrl() {
        OkHttpClient client=new OkHttpClient();
        String content = "";
        RequestBody body=new FormBody.Builder().add("content","{\"delta\":false,\"package\":\"com.shunyou.gifthelper\",\"appkey\":\"54d32548fd98c5fd57000a56\",\"sdk_version\":\"2.5.0.20141210\",\"type\":\"update\",\"channel\":\"1688wan\",\"old_md5\":\"9be6f645823329d5bb70d76008edd6a3\",\"proto_ver\":\"1.4\",\"idmd5\":\"528447f4ffb4e4824a2fd1d1f0cd62\",\"version_code\":\"10\"}").build();
        Request request=new Request.Builder().url("http://au.umeng.com/api/check_app_update").post(body).build();
        try {
            Response response=client.newCall(request).execute();
            content=response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

}
