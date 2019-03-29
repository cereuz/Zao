package com.onezao.zao.practices.security0306.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.onezao.zao.practices.security0306.utils.PreferenceCache;
import com.onezao.zao.zaov.R;

import java.lang.ref.WeakReference;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/29 17:01
 */
public class SplashVerifyActivity extends AppCompatActivity {
    MyHandler myHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myHandler = new MyHandler(this);
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initJump();

            }
        }, 2000);
    }

    private void initJump() {
        //指纹高优先级
        if (PreferenceCache.getFingerFlg()){
            //指纹已开启
            Intent intent = new Intent(SplashVerifyActivity.this,VerifyFingerActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        if (PreferenceCache.getGestureFlag()){
            Intent intent = new Intent(getApplicationContext(),ClosePatternPswActivity.class);
            //等于3为认证成功
            intent.putExtra("gestureFlg",3);
            startActivity(intent);
            finish();
            return;
        }
        startActivity(new Intent(getApplicationContext(), VerifyActivity.class));
        finish();
    }

    static class MyHandler extends Handler {
        private final WeakReference<SplashVerifyActivity> mActivty;

        public MyHandler(SplashVerifyActivity activity){
            mActivty =new WeakReference<SplashVerifyActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(myHandler != null){
            myHandler.removeCallbacksAndMessages(null);
            myHandler = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

