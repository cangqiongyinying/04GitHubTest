package com.jiangxin.notification_broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by my on 2016/8/3.
 */
public class PhoneCallReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Tools.ifCall=true;
        Tools.musicIsRunning=false;
    }
}
