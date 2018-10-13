package com.onezao.zao.practices.versionupdate0306;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onezao.zao.utils.AppVersionUtil;
import com.onezao.zao.utils.ConstantValue;
import com.onezao.zao.utils.DownloadUtil;
import com.onezao.zao.utils.StreamUtil;
import com.onezao.zao.utils.ToastUtil;
import com.onezao.zao.utils.ZaoUtils;
import com.onezao.zao.zaov.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class VersionUpdateActivity extends AppCompatActivity {

    long mVersionCode;
    String mVersionDes;
    String downloadUrl;

    //handler处理事件
    private static final int UPDAT_VERSION = 100;
    private static final int ENTER_HOME = 101;
    private static final int URL_ERROR = 102;
    private static final int IO_ERROR = 103;
    private static final int JSON_ERROR = 104;

    private static final int DOWNLOAD_FILE_SUCCESS = 201;
    private static final int DOWNLOAD_FILE_FAILED = 202;

    ProgressBar progressBar;
    TextView tv_version;
    ImageView iv_icon;
    private Context mContext;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case UPDAT_VERSION:
                    //弹出对话框，提示用户更新
                    showUpdateDialog();
                    break;
                case ENTER_HOME:
                    //进入应用程序主界面，Activity跳转
                    enterHome();
                    break;
                case URL_ERROR:
                    //网络链接错误
                    ToastUtil.showT(getApplicationContext(),"URL异常");
                    //取消对话框，进入应用程序主界面，Activity跳转
                    enterHome();
                    break;
                case IO_ERROR:
                    //文件读写错误
                    ToastUtil.showT(getApplicationContext(),"IO读取异常");
                    //取消对话框，进入应用程序主界面，Activity跳转
                    enterHome();
                    break;
                case JSON_ERROR:
                    //JSON数据有误
                    ToastUtil.showT(getApplicationContext(),"JSO数据解析异常");
                    //取消对话框，进入应用程序主界面，Activity跳转
                    enterHome();
                    break;
                case DOWNLOAD_FILE_SUCCESS:
                    //下载成功
                    ToastUtil.showT(getApplicationContext(),"下载成功");
                    break;
                case DOWNLOAD_FILE_FAILED:
                    //JSON数据有误
                    ToastUtil.showT(getApplicationContext(),"下载失败");
                    //进入应用程序主界面，Activity跳转
                    enterHome();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version_update_view);

        mContext = this;

        initUI();
        initData();
    }

    private void initUI() {
        tv_version = (TextView)findViewById(R.id.tv_version);
        iv_icon = (ImageView)findViewById(R.id.iv_icon);
        //进度条
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
    }

    public void versionUpdate(View view){
        checkVersion();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mVersionCode = getVersionCode();
        String zLabel = AppVersionUtil.getLabel(mContext);
        int zVersionCode = AppVersionUtil.getVersionCode(mContext);
        String zVersionName = AppVersionUtil.getVersionName(mContext);
        String zPackageName = mContext.getPackageName();
        Drawable zIcon = AppVersionUtil.getIcon(mContext);

        iv_icon.setImageDrawable(zIcon);
        tv_version.setText("Label : " + zLabel + "\n" + "PackageName : " + zPackageName + "\n" + "VersionCode : " + zVersionCode + "\n" + "VersionName : " + zVersionName);
        tv_version.setTextSize(22);
    }

    /**
     * 通过请求网络，获取版本号，检测是否有版本更新。
     */
    private void checkVersion() {
        //联网必须要在子线程处理，不然会有线程阻塞。这里注意要获取网络权限
        new Thread() {
            @Override
            public void run() {

                //睡眠几秒钟，使SplashActivity显示出来
                try {
                    Thread.sleep(ConstantValue.ONE_SECOND);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //将子线程的数据发送到主线程，通过handler处理
//                Message message = new Message();
                Message message = Message.obtain();

                //发送请求，获取数据，参数为请求json的链接地址
                try {
                    //1.封装 url 地址
                    URL url = new URL(ZaoUtils.CHECK_VERSION_JSON_URL);
                    //2 . 开启一个连接
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    //3. 设置常见请求头参数（请求头）
                    //请求超时
                    connection.setConnectTimeout(ZaoUtils.CHECK_VERSION_CONNECT_TIME);
                    //连接超时
                    connection.setReadTimeout(ZaoUtils.CHECK_VERSION_CONNECT_TIME);
                    //请求方式，默认是get
//                    connection.setRequestMethod("POST");
                    //4. 获取请求成功响应码，并对应做处理
                    if (connection.getResponseCode() == 200) {
                        //5. 以流的形式，将数据读取下来
                        InputStream is = connection.getInputStream();
                        //6. 将流转换成字符串 （工具类封装）
                        String json = StreamUtil.stream2String(is);
                        //打印json数据
                        Log.i(ConstantValue.TAG, json);
                        //7.解析JSON数据
                        JSONObject jsonObject = new JSONObject(json);
                        String versionName = jsonObject.getString("versionName");

                        mVersionDes = jsonObject.getString("versionDes");
                        String versionCode = jsonObject.getString("versionCode");
                        downloadUrl = jsonObject.getString("downloadUrl");
                        //打印解析的json内数据
                        Log.i(ConstantValue.TAG, versionName);
                        Log.i(ConstantValue.TAG, mVersionDes);
                        Log.i(ConstantValue.TAG, versionCode);
                        Log.i(ConstantValue.TAG, downloadUrl);

                        //8.比对json的版本号和本地APK的版本号。（如果网络版本号更大，提示用户更新。）
                        if (mVersionCode < Integer.parseInt(versionCode)) {
                            // 如果本地版本号小于线上版本，弹出对话框，提示用户是否下载  。  UI ，消息机制
                            message.what = UPDAT_VERSION;
                        } else {
                            //如果不小于，则直接进入主界面
                            message.what = ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    message.what = URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    message.what = IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    message.what = JSON_ERROR;
                } finally {
                    //
                    mHandler.sendMessage(message);
                }
            }
        }.start();
    }

    //获取版本号
    public int getVersionCode() {
        //1.包管理者对象 PackageManager
        PackageManager pm = getPackageManager();
        //2. 从包的管理者对象中，获取指定包名的基本信息（版本名称，版本号）,flag传0代表获取基本信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(),0);
//            return packageInfo.versionName + "\n" + packageInfo.packageName;
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return  0;
    }

    /**Empty
     * 弹出对话框，提示用户更新
     */
    private void showUpdateDialog() {
        //对话框，是依赖于Activity存在的。  this不可以少！！！
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        //设置左上角图标
        builder.setIcon(R.mipmap.ic_launcher);
        //设置标题和描述内容
        builder.setTitle(ZaoUtils.DIALOG_TITLE);
        builder.setMessage(mVersionDes);
        //确定按钮，立即更新
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressBar.setVisibility(View.VISIBLE);
                //下载APK文件
                downloadAPK(downloadUrl);

            }
        });
        //消极按钮，稍后再说
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //取消对话框，进入应用程序主界面，Activity跳转
                enterHome();
            }
        });

        //点击取消按钮 监听
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                //取消对话框，进入应用程序主界面，Activity跳转
                enterHome();
                //取消dialog
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    /**
     * 下载文件
     * @param url  下载地址
     */
    private void downloadAPK(String url) {
        DownloadUtil.get().download(url, "ame", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(final File file) {
                //子线程不能修改UI ，需要在主线程Toast
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtil.showT(getApplicationContext(), "下载完成");
                        progressBar.setVisibility(View.GONE);
                        //安装apk
                        installAPK(file);
                    }
                });
                Log.i(ConstantValue.TAG,"下载完成" + ZaoUtils.getSystemTime());
            }
            @Override
            public void onDownloading(int progress) {
                progressBar.setProgress(progress);
            }
            @Override
            public void onDownloadFailed() {
//                ToastUtil.showT(SplashActivity.this, "下载失败");
                Log.i(ConstantValue.TAG,"下载失败");
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    // 下载APK文件之后，安装文件
    private void installAPK(File file) {
        // 兼容7.0    Android 7.0 系统共享文件需要通过 FileProvider 添加临时权限，否则系统会抛出 FileUriExposedException .
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(),"com.onezao.zao.mobilesafe.fileprovider",file);
            intent.setDataAndType(contentUri,"application/vnd.android.package-archive");
            //调用安装APP的Activity
            startActivityForResult(intent, 0);
        } else {
            // 版本 N以下的，这样处理
            Log.e("OpenFile", file.getName());
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addCategory("android.intent.category.DEFAULT");
        /*        intent.setData(Uri.fromFile(file));
        intent.setType("application/vnd.android.package-archive");*/
            //上下两种 二选一
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
            startActivityForResult(intent, 0);
        }
    }

    //跳转到应用主界面 HomeActivity
    private void enterHome() {
        startActivity(new Intent(this,VersionUpdateActivity.class));
    }
}
