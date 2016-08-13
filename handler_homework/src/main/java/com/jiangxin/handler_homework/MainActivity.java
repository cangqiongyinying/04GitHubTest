package com.jiangxin.handler_homework;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private Button mBtn;
    private int time=60;
    private MyHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtn= (Button) findViewById(R.id.time_down_btn);
        mHandler=new MyHandler(this);
    }

    public void onClick(View view){
        mHandler.sendEmptyMessage(1);
        mBtn.setEnabled(false);
    }

    static class MyHandler extends Handler{
        private  WeakReference<MainActivity> activityWeakReference;
        public MyHandler(MainActivity mainActivity){
            activityWeakReference=new WeakReference<MainActivity>(mainActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            activityWeakReference.get().mBtn.setText(activityWeakReference.get().time+"s");

            if(activityWeakReference.get().time-->0){
                sendEmptyMessageDelayed(1,1000);
            }else {
                activityWeakReference.get().mBtn.setText("重新开始倒计时");
                activityWeakReference.get().mBtn.setEnabled(true);
                activityWeakReference.get().time=60;
            }
        }
    }
}
