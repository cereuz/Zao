package com.onezao.zao.practices.screenrecord0306;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.onezao.zao.bean.FileBean;
import com.onezao.zao.myapp.BaseActivity;
import com.onezao.zao.recycleview0306.OnItemClickListener;
import com.onezao.zao.utils.ConstantValue;
import com.onezao.zao.utils.FileUtils;
import com.onezao.zao.utils.ToastUtil;
import com.onezao.zao.utils.ZaoUtils;
import com.onezao.zao.zaov.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;


public class ScreenRecordActivity extends BaseActivity implements View.OnClickListener{
    private static final int PERMISSION_REQUEST_CODE  = 10;
    private static final int RECORD_REQUEST_CODE  = 101;
    private static final int STORAGE_REQUEST_CODE = 102;
    private static final int AUDIO_REQUEST_CODE   = 103;
    private static boolean SERVICE_IS_RUNNING   = false;

    private MediaProjectionManager projectionManager;
    private MediaProjection mediaProjection;
    private RecordService2 recordService;
    private ImageView startBtn;
    TextView tv_count;

    TextView tv_share;
    TextView tv_delete;
    TextView tv_start;
    TextView tv_setting;
    PopupWindow popupWindow;

    private MyRecordAdapter mAdapter;
    ArrayList<FileBean> list;
        RecyclerView rv_record_video;

    public int mPosition;

    private static final long delayMillis = 1000;
    public  int count = 3;
    Intent dataIntent;

    public Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    handler.removeMessages(1);
                    removeALLActivity();
                    break;
                case 1 :
                    countDown();
                    break;
                case 2 :
                    onResume();
                    break;
            }
        };
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        projectionManager = (MediaProjectionManager) getSystemService(MEDIA_PROJECTION_SERVICE);
        setContentView(R.layout.activity_screen_record_view);

        tv_count = (TextView)findViewById(R.id.tv_count);
        tv_count.setVisibility(View.GONE);
        startBtn = (ImageView) findViewById(R.id.start_record);
//        startBtn.setEnabled(false);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                if(SERVICE_IS_RUNNING){
                    if (recordService.isRunning()) {
                        recordService.stopRecord();
                        startBtn.setText(R.string.start_record);
                    } else {
                        Intent captureIntent = projectionManager.createScreenCaptureIntent();
                        startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
                    }
                } else {*/
                    Intent captureIntent = projectionManager.createScreenCaptureIntent();
                    startActivityForResult(captureIntent, RECORD_REQUEST_CODE);
                    startBtn.setVisibility(View.GONE);
//                }
            }
        });

        initRecordServicePermission();

    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    /**
     * 初始化界面内容
     */
    private void initView() {
        list = ZaoUtils.GetVideoFileAttr(this, FileUtils.getSaveDirectory(this));
        list.addAll(ZaoUtils.GetVideoFileAttr(this, FileUtils.getSaveDirectory(this, Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "ScreenRecord" + "/")));
        for(FileBean file : list){
            Log.e(ConstantValue.TAG,"文件名称 ：" + file.getFilename() + "\n" + "文件路径 ：" + file.getPath() + "\n" + "最后修改时间 ：" + ZaoUtils.tranTime(2,new Date(file.getDate())) + "\n" + "文件大小 ：" + file.getSize() + "\n" + "Bitmap : " + file.getBitmap());
        }

        rv_record_video = (RecyclerView)findViewById(R.id.rv_record_video);
        mAdapter = new MyRecordAdapter(this, list);
        rv_record_video.setLayoutManager(new LinearLayoutManager(this));
//        rv_record_video.addItemDecoration(new LineDecoration(32,1,this.getResources().getColor(R.color.colorPrimary)));
        rv_record_video.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FileBean tag =(FileBean)view.getTag();
                ToastUtil.showT(getApplicationContext(),list.get(position).getFilename() + "\n" + tag.getFilename());
                start_play_video(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getApplicationContext(),"TestItemOnLongClick", Toast.LENGTH_SHORT).show();
                //普通的弹出框
//                showPopMenu(view);
                //动画效果的弹出框
                mPosition = position;
                showPopupWindow(view);
            }
        });

        //设置ImageView的点击事件
        mAdapter.setOnItemImageViewClickListener(new MyRecordAdapter.ItemImageViewInterface(){
            @Override
            public void onclick(View view, int position) {
                Toast.makeText(getApplicationContext(),"点击了小图标 : " + position ,Toast.LENGTH_SHORT).show();
/*                //从数据库中删除数据
                mAppInfoList.delete(mAppInfoList.get(position).getPhone());
                //从集合中删除数据
                mAppInfoList.remove(position);
                //4.通知适配器，数据改变了。
                mAdapter.notifyDataSetChanged();*/
            }
        });
    }


    /**
     * 显示弹出框
     */
    private void showPopupWindow(View view) {
        View popupView = View.inflate(this,R.layout.popupwindow_appmanager_view,null);
        //设置透明
        ConstraintLayout cl_view = (ConstraintLayout) popupView.findViewById(R.id.cl_view);
        cl_view.getBackground().setAlpha(120);//0~255透明度值 0：全透明；255不透明

        tv_share = (TextView)popupView.findViewById(R.id.tv_share);
        tv_delete = (TextView)popupView.findViewById(R.id.tv_delete);
        tv_start = (TextView)popupView.findViewById(R.id.tv_start);
        tv_setting = (TextView)popupView.findViewById(R.id.tv_setting);

        tv_share.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_delete.setText("删除");
        tv_start.setOnClickListener(this);
        tv_setting.setOnClickListener(this);

        //透明动画（透明 -- > 不透明）
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(ConstantValue.ONE_SECOND);
        alphaAnimation.setFillAfter(true);
        //缩放动画
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                0,1,
                0,1,
                Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f
        );
        scaleAnimation.setDuration(ConstantValue.ONE_SECOND);
        scaleAnimation.setFillAfter(true);
        //动画集合set
        AnimationSet animationSet = new AnimationSet(true);
        //添加两个动画
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        //1.创建窗体对象，指定宽高
        popupWindow = new PopupWindow(popupView,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        //2.设置一个透明背景图片，如果不设值，返回键会不响应
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //3.指定窗体位置
        popupWindow.showAsDropDown(view,300,-view.getHeight() - 30);
        //执行动画集合set
        popupView.startAnimation(animationSet);
    }



    /**
     * 长按弹出选择框
     * @param view
     */
    public void showPopMenu(View view){
        PopupMenu menu = new PopupMenu(this,view);
        menu.getMenuInflater().inflate(R.menu.menu_screen_record,menu.getMenu());
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.screen_record_add_item:
                        Toast.makeText(getApplicationContext(), "Add selected", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.screen_record_delete_item:
                        Toast.makeText(getApplicationContext(), "Delete Selected", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
        menu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "关闭了", Toast.LENGTH_SHORT).show();
            }
        });
        menu.show();
    }


    /**
     * 绑定录屏服务
     */
    private void initRecordServicePermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SYSTEM_ALERT_WINDOW)
                != PackageManager.PERMISSION_GRANTED) {
            //初始化权限, 6.0以上申请系统权限，需要用户手动给出
            initPermission();
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
        }

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECORD_AUDIO}, AUDIO_REQUEST_CODE);
        }

/*        Intent intent = new Intent(this, RecordService2.class);
        bindService(intent, connection, BIND_AUTO_CREATE);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(recordService != null) {
            unbindService(connection);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PERMISSION_REQUEST_CODE && requestCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                    Toast.makeText(this,"需要允许自定义Toast需要的权限！！", Toast.LENGTH_SHORT);
                    initPermission();
                }
            }
        } else  if (requestCode == RECORD_REQUEST_CODE && resultCode == RESULT_OK) {

            /**
             * 初始化服务
             */
            Intent intent = new Intent(this, RecordService2.class);
            bindService(intent, connection, BIND_AUTO_CREATE);

            //发送一条空消息，空消息，不是没有消息，而是消息不携带数据，传入的第一个参数是what，就是msg.what，可以用来区分不同的消息
            handler.sendEmptyMessageDelayed(1, delayMillis);

            dataIntent = data;
/*            Bundle tom = data.getExtras();
            Log.i(ConstantValue.TAG,"蜗牛data" + tom.toString() + ZaoUtils.getSystemTimeMore(2));*/
        } else if (resultCode == RESULT_CANCELED){
            startBtn.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
         if (requestCode == STORAGE_REQUEST_CODE || requestCode == AUDIO_REQUEST_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                finish();
            }
        } else if(requestCode == PERMISSION_REQUEST_CODE || requestCode == AUDIO_REQUEST_CODE){
             if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                 finish();
             }
         }
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            RecordService2.RecordBinder binder = (RecordService2.RecordBinder) service;
            recordService = binder.getRecordService();
            recordService.setConfig(metrics.widthPixels, metrics.heightPixels, metrics.densityDpi);
            startBtn.setEnabled(true);
//            startBtn.setText(recordService.isRunning() ? R.string.stop_record : R.string.start_record);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {}
    };


    /**
     * 初始化权限
     */
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (! Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent,PERMISSION_REQUEST_CODE);
            }
        }
    }

    public void countDown(){
        if(count == 0) {
/*            Bundle tom = dataIntent.getExtras();
            Log.i(ConstantValue.TAG,"蜗牛2data" + tom.toString() + "   " + ZaoUtils.getSystemTimeMore(2));*/

            mediaProjection = projectionManager.getMediaProjection(RESULT_OK, dataIntent);
            recordService.setMediaProject(mediaProjection);
            recordService.startRecord();
            SERVICE_IS_RUNNING = true;
            handler.sendEmptyMessage(0);
            return;
        }

        tv_count.setVisibility(View.VISIBLE);
        tv_count.setText(count + "");
        count--;
        Message  msg  =  Message.obtain();
        msg.what = 1;
        //sendMessageDelayed 发送一条延迟执行的消息，这条消息会延迟1000毫秒执行
        handler.sendMessageDelayed(msg, delayMillis);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.tv_delete:
                FileUtils.delete(this,list.get(mPosition).getPath());
                ToastUtil.showT(getApplicationContext(),"tv_delete ： 删除成功！");
                handler.sendEmptyMessage(2);
                break;

            case R.id.tv_share :
                ToastUtil.showT(this,"分享");
                //发送信息发送意图给短信app
                intent = new Intent(Intent.ACTION_SEND);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                intent.setType("*/*");//多个文件格式
                //信息内容
                intent.putExtra(Intent.EXTRA_TEXT, "好消息，好消息[ "
                        + "Zao ]非常好用，你值得拥有，下载地址:http://www.zao.com/app/");
                startActivity(intent);
                break;

            case R.id.tv_start :
                start_play_video(mPosition);
                break;

            case R.id.tv_setting :
                ToastUtil.showT(getApplicationContext(),"tv_setting");
                break;
        }
        if(popupWindow != null){
            popupWindow.dismiss();
        }
    }

    /**
     * 开始播放列表点击条目的视频
     */
    private void start_play_video(int position) {
        Intent intent;
        ToastUtil.showT(getApplicationContext(),"tv_start");
        Uri uri;
        File file = new File(list.get(position).getPath());
        intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(this, FileUtils.FILE_PROVIDER_AUTHORITIES, file);
            // 给目标应用一个临时授权
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "video/*");
        startActivity(intent);
    }
}

