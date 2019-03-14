package com.onezao.zao.practices.eventbus0306.order;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.onezao.zao.practices.eventbus0306.MessageEvent;
import com.onezao.zao.utils.LogZ;
import com.onezao.zao.zaov.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/14 11:12
 */

/**
 * E/EventBusOrderActivity.onMessageEvent5(L:66): onMessageEvent5(), message is Hello EventBus!
 * E/EventBusOrderActivity.onMessageEvent4(L:61): onMessageEvent4(), message is Hello EventBus!
 * E/EventBusOrderActivity.onMessageEvent3(L:54): onMessageEvent3(), message is Hello EventBus!
 */

public class EventBusOrderActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_order_view);
        initContentView();
        // 注册订阅者
        EventBus.getDefault().register(this);
        LogZ.e("注册订阅者");
    }

    private void initContentView() {
        Button btn_order = (Button)findViewById(R.id.btn_order_start_activity);
        btn_order.setText("EventBusOrderActivity");
        btn_order.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_order_start_activity) {
            EventBusOrderSecondActivity.start(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 1)
    public void onMessageEvent1(MessageEvent event) {
        LogZ.e("onMessageEvent1(), message is " + event.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 2)
    public void onMessageEvent2(MessageEvent event) {
        LogZ.e("onMessageEvent2(), message is " + event.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 3)
    public void onMessageEvent3(MessageEvent event) {
        LogZ.e("onMessageEvent3(), message is " + event.getMessage());
        // 取消事件
        EventBus.getDefault().cancelEventDelivery(event);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 4)
    public void onMessageEvent4(MessageEvent event) {
        LogZ.e( "onMessageEvent4(), message is " + event.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.POSTING, priority = 5)
    public void onMessageEvent5(MessageEvent event) {
        LogZ.e( "onMessageEvent5(), message is " + event.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }
}
