package com.onezao.zao.practices.eventbus0306;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.onezao.zao.utils.LogZ;
import com.onezao.zao.zaov.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/13 17:47
 */

/**
 * E/EventBusActivity.onMessageEventBackground(L:87): onMessageEventBackground(), current thread is zw
 * E/EventBusActivity.onMessageEventAsync(L:92): onMessageEventAsync(), current thread is pool-1-thread-2
 * E/EventBusActivity.onMessageEventPosting(L:72): onMessageEventPosting(), current thread is zw
 * E/EventBusActivity.onMessageEvent(L:65): message is Hello EventBus!  EventBusThirdActivity
 *      2019-03-14 10:28:16
 * E/EventBusActivity.onMessageEventMain(L:77): onMessageEventMain(), current thread is main
 * E/EventBusActivity.onMessageEventMainOrdered(L:82): onMessageEventMainOrdered(), current thread is main
 */

public class EventBusActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_view);
        initContentView();
        // 注册订阅者
        EventBus.getDefault().register(this);
    }

    private void initContentView() {
        Button btnStart = findViewById(R.id.btn_eventbus_start_activity);
        Button btnStart_Third = findViewById(R.id.btn_eventbus_start_activity_third);
        mTvMessage = findViewById(R.id.tv_eventbus_message);
        btnStart.setOnClickListener(this);
        btnStart_Third.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_eventbus_start_activity) {
            LogZ.e("The Button is clicked.== EventBusSecondActivity");
            EventBusSecondActivity.start(this);
        } else if(v.getId() == R.id.btn_eventbus_start_activity_third){
            LogZ.e("The Button is clicked.== EventBusThirdActivity");
            EventBusThirdActivity.start(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        LogZ.e("message is " + event.getMessage());
        // 更新界面
        mTvMessage.setText(event.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onMessageEventPosting(MessageEvent event) {
        LogZ.e( "onMessageEventPosting(), current thread is " + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEventMain(MessageEvent event) {
        LogZ.e( "onMessageEventMain(), current thread is " + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN_ORDERED)
    public void onMessageEventMainOrdered(MessageEvent event) {
        LogZ.e( "onMessageEventMainOrdered(), current thread is " + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEventBackground(MessageEvent event) {
        LogZ.e( "onMessageEventBackground(), current thread is " + Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onMessageEventAsync(MessageEvent event) {
        LogZ.e( "onMessageEventAsync(), current thread is " + Thread.currentThread().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }
}
