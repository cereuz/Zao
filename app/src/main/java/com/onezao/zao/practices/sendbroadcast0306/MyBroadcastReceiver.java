package com.onezao.zao.practices.sendbroadcast0306;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //接收数据
        String extra = intent.getStringExtra("key");
        String action = intent.getAction();
        Toast.makeText(context, "接收到了自定义的广播！\nextra："+extra+"\nAction="+action, Toast.LENGTH_LONG).show();
    }

}
