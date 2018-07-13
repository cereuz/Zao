package com.onezao.zao.practices.applistener0306;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class AppInstallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Uri data = intent.getData();
        String type = intent.getType();
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            Toast.makeText(context, data + "---PACKAGE_ADDED" + type,
                    Toast.LENGTH_SHORT).show();
        } else if ("android.intent.action.PACKAGE_INSTALL".equals(action)) {
            Toast.makeText(context, data + "---PACKAGE_INSTALL",
                    Toast.LENGTH_SHORT).show();
        } else if ("android.intent.action.PACKAGE_REMOVED".equals(action)) {
            Toast.makeText(context, data + "---PACKAGE_REMOVED",
                    Toast.LENGTH_SHORT).show();
        }
    }
}