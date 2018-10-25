package com.onezao.zao.practices.mynotitification0306;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.onezao.zao.recycleview0306.RecycleViewActivity;
import com.onezao.zao.utils.ConstantValue;
import com.onezao.zao.utils.ToastUtil;
import com.onezao.zao.utils.ZaoUtils;
import com.onezao.zao.zaov.AdminActivity;
import com.onezao.zao.zaov.R;

public class NotificationActivity extends AppCompatActivity{
      TextView tv_time;

    private static final long delayMillis = 1000;
    public  int count = 0;
    private long timer = 0;
    public Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    handler.removeMessages(1);
                    ToastUtil.showT(getApplicationContext(),"这里需要停止计时！！");
                    return;
                case 1 :
                    countDown();
            }
        };
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynotification_view);

        createMyNotificationChannel();
        initView();
    }

    private void initView() {
        tv_time = (TextView)findViewById(R.id.tv_time);

        handler.sendEmptyMessageDelayed(1,delayMillis * 3);
    }

    /**
     * 创建我的通知频道
     */
    private void createMyNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "我的聊天消息";
            int importance = NotificationManager.IMPORTANCE_MAX;
            createNotificationChannel(channelId, channelName, importance);
            channelId = "subscribe";
            channelName = "我的订阅消息";
            importance = NotificationManager.IMPORTANCE_MIN;
            createNotificationChannel(channelId, channelName, importance);
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        //显示桌面角标
        channel.setShowBadge(true);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }


    public void sendChatMsg() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel("chat");
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
            }
        }
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setContentTitle("收到一条聊天消息")
                .setContentText("今天中午吃什么？")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setNumber(6)  //传入创建角标的数量
                .build();
        manager.notify(1, notification);
    }

    public void sendSubscribeMsg() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this, "subscribe")
                .setContentTitle("收到一条订阅消息")
                .setContentText("地铁沿线30万商铺抢购中！")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setNumber(108)  //传入创建角标的数量
                .build();
        manager.notify(2, notification);
    }

    public void sendHangMsg(){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder = new Notification.Builder(this,"chat");
        Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fone.taobao.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setAutoCancel(true);
        builder.setContentTitle("悬挂式通知");
        //设置点击跳转
        Intent hangIntent = new Intent();
        hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        hangIntent.setClass(this, AdminActivity.class);
        //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
        PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setFullScreenIntent(hangPendingIntent, true);
        builder.setVisibility(Notification.VISIBILITY_PUBLIC);
        manager.notify(2, builder.build());
    }


    private void send_zhedie_notification() {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fone.taobao.com/"));
        PendingIntent pendingIntent =PendingIntent.getActivity(this,0,intent,0);
        NotificationChannel b;
        NotificationCompat.Builder mBuilder2 = new NotificationCompat.Builder(this);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            b = new NotificationChannel("6890","货流信息", NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(b);
            mBuilder2.setChannelId("6890");
        }
        //下拉时的样式
        RemoteViews show = new RemoteViews(getPackageName(),R.layout.notification_scoller_view);
        show.setTextViewText(R.id.tv_notification_title,"surprise");
        show.setImageViewResource(R.id.iv_notification_pic,R.mipmap.ic_launcher);
        //未下拉的样式
        RemoteViews collapsed = new RemoteViews(getPackageName(),R.layout.item_base);
        mBuilder2.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher))
                .setContentTitle("折叠式")
                .setCustomContentView(collapsed)
                .setCustomBigContentView(show)
                .setContentIntent(pendingIntent);
        manager.notify(0,mBuilder2.build());
    }

    public void countDown(){
        Log.i(ConstantValue.TAG,timer + 1000 +"");
        timer += 1000;
        tv_time.setText(ZaoUtils.getSystemTimeMore(1));
        count++;
        if (count < 10){
            sendChatMsg();
            sendSubscribeMsg();
            send_zhedie_notification();
            sendHangMsg();
        }
        Message  msg  =  Message.obtain();
        msg.what = 1;
        //sendMessageDelayed 发送一条延迟执行的消息，这条消息会延迟1000毫秒执行
        handler.sendMessageDelayed(msg, delayMillis * 6);
    }

}
