package com.onezao.zao.practices.security0306.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.onezao.zao.practices.security0306.utils.ChaosGestureView;
import com.onezao.zao.practices.security0306.utils.PreferenceCache;
import com.onezao.zao.zaov.R;

import java.util.List;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/29 17:06
 */
public class SettingPatternPswActivity extends AppCompatActivity implements ChaosGestureView.GestureCallBack{
    private TextView tv_back;
    private ChaosGestureView gestureView;
    private int jumpFlg;
    private int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_pattern_psw);
        jumpFlg = getIntent().getIntExtra("jumpFlg", 0);
        flag = getIntent().getIntExtra("flag", 0);
        initView();
    }

    private void initView() {
        tv_back = (TextView) findViewById(R.id.tv_setting_back);
        gestureView = (ChaosGestureView) findViewById(R.id.gesture);
        gestureView.setGestureCallBack(this);
        //不调用这个方法会造成第二次启动程序直接进入手势识别而不是手势设置
        gestureView.clearCache();
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void gestureVerifySuccessListener(int stateFlag, List<ChaosGestureView.GestureBean> data, boolean success) {
        if (stateFlag == ChaosGestureView.STATE_LOGIN) {
            PreferenceCache.putGestureFlag(true);
            finish();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
