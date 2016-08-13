package com.jiangxin.downloadservice_activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Messenger messenger;
    private ProgressBar mProgress;
    private TextView mProgressTv;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mProgress.setMax(msg.arg2);
            Log.d("downloadget",msg.arg2+"");
            mProgress.setProgress(msg.arg1);
            mProgressTv.setText(msg.arg1+"/"+msg.arg2);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mProgress= (ProgressBar) findViewById(R.id.download_progress);
        mProgressTv= (TextView) findViewById(R.id.progress_text);
    }

    public void click(View view){
//        Intent intent=new Intent(this,DownloadService.class);
//        bindService(intent,serviceConnection, Service.BIND_AUTO_CREATE);
        Intent intent=new Intent();
        intent.setAction("androidxx.intent.action.REPORT");
        intent.setPackage("com.jiangxin.downloadservice_activity");
        bindService(intent,serviceConnection,Service.BIND_AUTO_CREATE);
    }

    ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("downloadget","已连接");
            messenger=new Messenger(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
