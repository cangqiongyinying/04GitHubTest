package com.jiangxin.httplibrary;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by my on 2016/8/11.
 */
public class HttpUtils {
    private static ExecutorService executorService;

    public static HttpThread load(String path){
        if (executorService==null){
            executorService= Executors.newFixedThreadPool(3);
        }
        HttpThread httpThread=new HttpThread();
        httpThread.start(path);
        return httpThread;
    }

    public static class HttpThread{
        private HttpCallback httpCallback;
        private int requestCode;
        private boolean isPost;
        private String params;


        public void callback(HttpCallback httpCallback,int requestCode){
            this.httpCallback=httpCallback;
            this.requestCode=requestCode;
        }

        public HttpThread post(Map<String,Object> param){
            isPost=true;
            Set<String> keySet=param.keySet();
            StringBuilder builder = new StringBuilder();
            int count=0;
            for(String key:keySet) {
                Object value = param.get(key);
                if (count<keySet.size()-1){
                    builder.append(key).append("=").append(value).append("&");
                }else {
                    builder.append(key).append("=").append(value);
                }
                ++count;
            }
            this.params=builder.toString();
            return this;
        }
        protected void start(String path){
            executorService.execute(new HttpTask(path));
        }

        private Handler mHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String result=msg.obj.toString();
                if (httpCallback!=null){
                    httpCallback.success(result,requestCode);
                }
            }
        };
        class HttpTask implements Runnable{

            private String path;
            public HttpTask(String path){
                this.path=path;
            }
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setConnectTimeout(5000);
                    if (isPost){
                        urlConnection.setDoOutput(true);
                        urlConnection.setRequestMethod("POST");
                        urlConnection.getOutputStream().write(params.getBytes());
                        urlConnection.getOutputStream().flush();
                    }
                    urlConnection.connect();
                    if (urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK){
                        InputStream inputStream = urlConnection.getInputStream();
                        StringBuilder stringBuilder = new StringBuilder();
                        int len=0;
                        byte[] buffer=new byte[1024];
                        while ((len=inputStream.read(buffer))!=-1){
                            stringBuilder.append(new String(buffer,0,len));
                        }
                        String result=stringBuilder.toString();
                        Message message=mHandler.obtainMessage();
                        message.obj=result;
//                    mHandler.sendMessage(message);
                        message.sendToTarget();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
