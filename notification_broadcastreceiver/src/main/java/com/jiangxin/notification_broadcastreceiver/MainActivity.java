package com.jiangxin.notification_broadcastreceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NotificationManager mNotificationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取NotificationManager对象（系统级的）
        mNotificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    /*
    * Button的点击响应
    * */
    public void onClick(View view){
        if(view.getId()==R.id.start_btn){
            Tools.ifCall=false;
            Tools.progress=0;
            MusicStart();
        }else if (view.getId()==R.id.restart_btn){
            if (Tools.ifCall&&!Tools.musicIsRunning) {
                Tools.ifCall = false;
                MusicStart();
            }else {
                Toast.makeText(this,"未开始播放或播放中",Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }

    private void MusicStart(){
        //判断SDK版本决定builder对象的创建类型
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN){
            //创建系统的Notification.Builder对象
            final Notification.Builder builder=new Notification.Builder(this);
            //设置标题栏
            builder.setContentTitle("音乐播放");
            //设置图标
            builder.setSmallIcon(R.drawable.home_icon_customer_service_def);
            //设置初始进度
            builder.setProgress(100,Tools.progress,false);
            onPending(builder);
            //开启一个子线程，用于刷新进度
            if (!Tools.musicIsRunning){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (Tools.progress < 100 && !Tools.ifCall) {
                            Tools.musicIsRunning=true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            builder.setContentText("播放进度："+Tools.progress +"/100" );
                            builder.setProgress(100,++Tools.progress,false);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                onPending(builder);
                                //每次设置完都需要提交一次，刷新通知栏
                                mNotificationManager.notify(1,builder.build());
                            }
                        }
                        //播放完毕自动取消通知栏
                        while (Tools.progress>=100){
                            mNotificationManager.cancel(1);
                            Tools.musicIsRunning=false;
                        }
                    }
                }).start();
            }
            //注意：在主线程中也必须提交，才能完成通知的创建
            mNotificationManager.notify(1,builder.build());
        }else {
            //创建v7包下的NotificationCompat.Builder对象
            final NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
            //设置标题栏
            builder.setContentTitle("音乐播放");
            //设置图标
            builder.setSmallIcon(R.drawable.home_icon_customer_service_def);
            //设置初始进度
            builder.setProgress(100,Tools.progress,false);
            onPendingCompat(builder);
            //开启一个子线程，用于刷新进度
            if(!Tools.musicIsRunning){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (Tools.progress < 100 && !Tools.ifCall) {
                            Tools.musicIsRunning=true;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            builder.setContentText("播放进度："+Tools.progress +"/100" );
                            builder.setProgress(100,++Tools.progress,false);
                            onPendingCompat(builder);
                            //每次设置完都需要提交一次，刷新通知栏
                            mNotificationManager.notify(1,builder.build());
                        }
                        //播放完毕自动取消通知栏
                        while (Tools.progress>=100) {
                            mNotificationManager.cancel(1);
                            Tools.musicIsRunning=false;
                        }
                    }
                }).start();
            }
            //注意：在主线程中也必须提交，才能完成通知的创建
            mNotificationManager.notify(1,builder.build());

        }
    }
    /*
    * 设置点击方法，使通知框可回到APP
    * 缺陷：子线程模拟Service使通知栏不断提交，同时点击事件也需要对应设置
    * */
    private void onPending(Notification.Builder builder){
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
    }

    /*
    * v7包下的点击方法，版本16以下会使用
    * */
    private void onPendingCompat(NotificationCompat.Builder builder){
        Intent intent=new Intent(this,MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,1,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(pendingIntent);
    }

}
