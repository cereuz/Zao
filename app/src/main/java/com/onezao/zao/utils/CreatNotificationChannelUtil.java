package com.onezao.zao.utils;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class CreatNotificationChannelUtil {
    /**
     * 创建我的通知频道
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void createNotificationChannel(Context context,String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        //显示桌面角标
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
}
