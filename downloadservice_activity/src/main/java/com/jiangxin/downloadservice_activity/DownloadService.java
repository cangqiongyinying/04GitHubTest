package com.jiangxin.downloadservice_activity;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by my on 2016/8/9.
 */
public class DownloadService extends Service {

    private Messenger serviceMessenger;
    private Messenger messenger;
    private int progress;
    private int totalLen ;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    public void onCreate() {
        super.onCreate();
        serviceMessenger=new Messenger(mHandler);
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                ByteArrayOutputStream baos = null;
                Message message=new Message().obtain();
                try {

                    //获得URL对象
                    URL url = new URL("http://i3.72g.com/upload/201510/201510261436311061.png");
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    totalLen = urlConnection.getContentLength();
                    message.arg2=totalLen;
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                        //获得图片网络流(存放的就是图片资源)
                        inputStream = urlConnection.getInputStream();
                        //图片流转换成字节数组
                        baos = new ByteArrayOutputStream();
                        float downloadLen = 0;
                        byte[] buffer = new byte[1024];
                        int len = 0;
                        while((len = inputStream.read(buffer)) != -1) {
                            baos.write(buffer,0,len);
                            downloadLen+=len;
                            if (downloadLen>0){
                                progress= (int) downloadLen;
                                message.arg1=progress;
                                messenger.send(message);
//                                Log.v("downloadtext:",progress+"");
                            }
                        }
                        baos.flush();
                        inputStream.close();
//                        mHandler.sendEmptyMessage(0);
                        //将ByteArrayOutputStream转换成byte数组
//                        byte[] bytes = baos.toByteArray();
                        //将byte数组转换成Bitmap
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        Message message = mHandler.obtainMessage();
//                        message.obj = bitmap;
//                        mHandler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return serviceMessenger.getBinder();
    }

}