package com.onezao.zao.practices.eventbus0306;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.onezao.zao.utils.ZaoUtils;
import com.onezao.zao.zaov.R;

import org.greenrobot.eventbus.EventBus;

/**
 * @author : zw
 * @email : zsky@live.com,
 * @motto : To be, or not to be.
 * @date : 2019/3/13 17:52.
 */

public class EventBusThirdActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Context context) {
        Intent intent = new Intent(context, EventBusThirdActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_other);
        initContentView();
    }

    private void initContentView() {
        findViewById(R.id.btn_other_post_event).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_other_post_event) {

            new Thread("zw") {
                @Override
                public void run() {
                    // 发布事件
                    EventBus.getDefault().post(new MessageEvent("Hello EventBus!  EventBusThirdActivity \n " + ZaoUtils.getSystemTimeMore(2)));
                }
            }.start();
        }
    }
}
