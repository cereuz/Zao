package com.onezao.zao.practices.screenrecord0306;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.display.DisplayManager;
import android.hardware.display.VirtualDisplay;
import android.media.MediaRecorder;
import android.media.projection.MediaProjection;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.onezao.zao.utils.ConstantValue;
import com.onezao.zao.utils.FileUtils;
import com.onezao.zao.utils.ToastUtil;
import com.onezao.zao.utils.ZaoUtils;
import com.onezao.zao.zaov.R;

import java.io.IOException;

import static android.os.Build.VERSION_CODES.O;


public class RecordService2 extends Service {
  private MediaProjection mediaProjection;
  private MediaRecorder mediaRecorder;
  private VirtualDisplay virtualDisplay;

  private boolean running;
  private int width = 720;
  private int height = 1080;
  private int dpi;

  Button btn_record;
  TextView tv_record_time;

  WindowManager mWM;
  int mScreenWidth;
  int mScreenHeight;
  View mRocketView;
  WindowManager.LayoutParams params;
  //自定义Toast
  WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();


  //通知控制类
  NotificationManager manager;

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
  public IBinder onBind(Intent intent) {
    return new RecordBinder();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    HandlerThread serviceThread = new HandlerThread("service_thread",
        android.os.Process.THREAD_PRIORITY_BACKGROUND);
    serviceThread.start();
    running = false;
    mediaRecorder = new MediaRecorder();

    //通知消息
    manager  = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    //获取屏幕的宽高
    mWM = (WindowManager)getSystemService(WINDOW_SERVICE);
    mScreenWidth = mWM.getDefaultDisplay().getWidth();
    mScreenHeight = mWM.getDefaultDisplay().getHeight();

    //开启火箭
    showRocket();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  public void setMediaProject(MediaProjection project) {
    mediaProjection = project;
  }

  public boolean isRunning() {
    return running;
  }

  public void setConfig(int width, int height, int dpi) {
    this.width = width;
    this.height = height;
    this.dpi = dpi;
  }

  public boolean startRecord() {
    if (mediaProjection == null || running) {
      return false;
    }

    initRecorder(this);
    createVirtualDisplay();
    mediaRecorder.start();
    running = true;
    return true;
  }

  public boolean stopRecord() {
    if (!running) {
      return false;
    }
    running = false;
    mediaRecorder.stop();
    mediaRecorder.reset();
    virtualDisplay.release();
    mediaProjection.stop();

    return true;
  }

  private void createVirtualDisplay() {
    virtualDisplay = mediaProjection.createVirtualDisplay("MainScreen", width, height, dpi,
        DisplayManager.VIRTUAL_DISPLAY_FLAG_AUTO_MIRROR, mediaRecorder.getSurface(), null, null);
  }

  private void initRecorder(Context context) {
    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
    mediaRecorder.setVideoSource(MediaRecorder.VideoSource.SURFACE);
    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
    mediaRecorder.setOutputFile(FileUtils.getSaveDirectory(context) + ZaoUtils.getSystemTimeMore(1,System.currentTimeMillis()) + ".mp4");
    mediaRecorder.setVideoSize(width, height);
    mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
    mediaRecorder.setVideoFrameRate(30);
    try {
      mediaRecorder.prepare();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public class RecordBinder extends Binder {
    public RecordService2 getRecordService() {
      return RecordService2.this;
    }
  }


  //开启火箭
  private void showRocket() {
    //界面布局的管理者对象
    params = mParams;

    params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    params.width = WindowManager.LayoutParams.WRAP_CONTENT;
    params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                     | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE   //默认能够被触摸
            | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
    params.format = PixelFormat.TRANSLUCENT;
/*        //在响铃的时候显示Toast，和电话类型一致
        params.type = WindowManager.LayoutParams.TYPE_PHONE;*/
    /**
     * 兼容8.0
     */
    if (Build.VERSION.SDK_INT > 25) {
      params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
    } else {
      params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
    }
    params.setTitle("Toast");
    //指定Toast的所在位置(将吐司指定在左上角)
    params.gravity = Gravity.LEFT + Gravity.TOP;

    //定义吐司所在的布局，并且将其转换成view对象，添加至窗体（权限）
    mRocketView = View.inflate(getApplicationContext(), R.layout.service_rocket_view,null);
    //定义按钮点击事件
    tv_record_time = (TextView)mRocketView.findViewById(R.id.tv_item_record_time);
    btn_record = (Button)mRocketView.findViewById(R.id.btn_record);
    btn_record.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        if (running) {
          handler.sendEmptyMessage(0);
          ToastUtil.showT(getApplicationContext(),"停止录屏幕");
          stopRecord();
          mRocketView.setVisibility(View.GONE);

/*        *//**
         * 发送广播
         *//*
        Intent intent = new Intent();
        intent.setAction("com.onezao.record.broadcast.action");
        //发送数据
        intent.putExtra("record","录制视频");
        sendBroadcast(intent);*/

        /**
         * 发送通知消息
         */
         sendNotification();
        /*        } else {*/
/*          //调用系统应用的Activity
          Intent intent = new Intent(getApplicationContext(),TransRecordActivity.class);
          //开启火箭后，关闭了唯一的Activity对应的任务栈，所以在此次需要告知新开启的Activity开辟一个新的任务栈
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
        ToastUtil.showT(getApplicationContext(),"开始录屏幕");
          Intent captureIntent = projectionManager.createScreenCaptureIntent();
          startActivityForResult(captureIntent, RECORD_REQUEST_CODE);*//*
          startRecord();
          btn_record.setText(R.string.stop_record);*/
//        }
      }
    });

    /**
     * 定时器应用，进入服务，直接录屏，并计时。
     */
    startRecord();
    btn_record.setText(R.string.stop_record);
    tv_record_time.setText(ZaoUtils.tranTime(1,timer - 8*60*60*1000 + "" ));
    //发送一条空消息，空消息，不是没有消息，而是消息不携带数据，传入的第一个参数是what，就是msg.what，可以用来区分不同的消息
    handler.sendEmptyMessageDelayed(1, delayMillis * 5);

    //执行图片动画
    ImageView iv_rocket = (ImageView)mRocketView.findViewById(R.id.iv_rocket);
    AnimationDrawable animationDrawable = (AnimationDrawable)iv_rocket.getBackground();
    animationDrawable.start();

    mWM.addView(mRocketView,params);

    //火箭升空,小火箭的拖拽事件
    mRocketView.setOnTouchListener(new View.OnTouchListener() {
      int startX;
      int startY;
      //对不同的事件做不同的处理
      @Override
      public boolean onTouch (View view, MotionEvent motionEvent){
        switch (motionEvent.getAction()) {
          case MotionEvent.ACTION_DOWN:
            Log.i(ConstantValue.TAG, "ACTION_DOWN" + ZaoUtils.getSystemTimeMore(2));
            startX = (int) motionEvent.getRawX();
            startY = (int) motionEvent.getRawY();
            break;

          case MotionEvent.ACTION_MOVE:
            Log.i(ConstantValue.TAG, "ACTION_MOVE" + ZaoUtils.getSystemTimeMore(2));
            int moveX = (int) motionEvent.getRawX();
            int moveY = (int) motionEvent.getRawY();

            int disX = moveX - startX;
            int disY = moveY - startY;

            params.x = params.x + disX;
            params.y = params.y + disY;

            //容错处理
            //左侧不能超出界面
            if (params.x < 0) {
              params.x = 0;
            }
            //上侧不能超出界面
            if (params.y < 0) {
              params.y = 0;
            }
            if (params.x > mScreenWidth - mRocketView.getWidth()) {
              params.x = mScreenWidth - mRocketView.getWidth();
            }
            if (params.y > mScreenHeight - mRocketView.getHeight() - 32) {
              params.y = mScreenHeight - mRocketView.getHeight() - 32;
            }

            //告知窗体，吐司需要按照手势的移动，去做位置的更新。
            mWM.updateViewLayout(mRocketView, params);

            //3.展示坐标修改之后，重置一次起始坐标
            startX = (int) motionEvent.getRawX();
            startY = (int) motionEvent.getRawY();
            break;

          case MotionEvent.ACTION_UP:
            Log.i(ConstantValue.TAG, "ACTION_UP" + ZaoUtils.getSystemTimeMore(2));
            //火箭拖拽到指定区域的时候放手（抬起）才可以被发射
            if(params.x > 100 && params.x < 600 && params.y >  360){
/*              //发射火箭
//              sendRocket();
              //产生尾气的Activity
              Intent intent = new Intent(getApplicationContext(),RecycleViewActivity.class);
              //开启火箭后，关闭了唯一的Activity对应的任务栈，所以在此次需要告知新开启的Activity开辟一个新的任务栈
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              startActivity(intent);*/
              ToastUtil.showT(getApplicationContext(),"移动到了这里，可以使用你的方法了！！");
            }
            break;
        }
        //在当前情况(只有touch事件，没有点击事件)下，返回false 不响应事件，返回true才会响应事件
        //既要响应拖拽事件，又要响应点击事件，则此结果需要返回false  [onTouchListener和onClickListener同时调用了的时候]
        return true;
      }
    });

  }

  /**
   * 发送通知消息
   */

  public void sendNotification(){
    if(Build.VERSION.SDK_INT >= O) {
      NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
      Notification.Builder builder = new Notification.Builder(this,"chat");
      //设置之后，自动跳转
      Intent hangIntent = new Intent();
      hangIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      hangIntent.setClass(this, ScreenRecordActivity.class);
      //如果描述的PendingIntent已经存在，则在产生新的Intent之前会先取消掉当前的
      PendingIntent hangPendingIntent = PendingIntent.getActivity(this, 0, hangIntent, PendingIntent.FLAG_CANCEL_CURRENT);
      builder.setContentIntent(hangPendingIntent);

      builder.setContentIntent(hangPendingIntent);
      builder.setSmallIcon(R.mipmap.ic_launcher);
      builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
      builder.setAutoCancel(true);
      builder.setContentTitle("我是标题");
      builder.setContentText("我是蜗牛");//通知内容
      Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fone.taobao.com/"));
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);

      //设置点击跳转
      builder.setFullScreenIntent(pendingIntent, true);
      builder.setVisibility(Notification.VISIBILITY_PUBLIC);
      manager.notify(2, builder.build());
    } else {
      NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
      Notification.Builder builder = new Notification.Builder(this);
      Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://fone.taobao.com/"));
      PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);
      builder.setContentIntent(pendingIntent);
      builder.setSmallIcon(R.mipmap.ic_launcher);
      builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
      builder.setAutoCancel(true);
      builder.setContentTitle("我是标题");
      builder.setContentText("我是蜗牛");//通知内容
      //设置点击跳转
      builder.setFullScreenIntent(pendingIntent, true);
      builder.setVisibility(Notification.VISIBILITY_PUBLIC);
      manager.notify(2, builder.build());
    }
  }

  public void countDown(){
    Log.i(ConstantValue.TAG,timer + 1000 +"");
    timer += 1000;
    tv_record_time.setText(ZaoUtils.tranTime(1,timer - 8*60*60*1000 + ""));
    count++;
    Message  msg  =  Message.obtain();
    msg.what = 1;
    //sendMessageDelayed 发送一条延迟执行的消息，这条消息会延迟1000毫秒执行
    handler.sendMessageDelayed(msg, delayMillis);
  }
}