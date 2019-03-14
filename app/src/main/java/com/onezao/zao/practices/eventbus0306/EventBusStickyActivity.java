package com.onezao.zao.practices.eventbus0306;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * @date : 2019/3/14 10:45
 */
public class EventBusStickyActivity extends AppCompatActivity {
    private TextView mTvMessage;

    public static void start(Context context) {
        Intent intent = new Intent(context, EventBusStickyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventbus_other);
        initContentView();
        // 注册订阅者
        EventBus.getDefault().register(this);
    }

    private void initContentView() {
        mTvMessage = findViewById(R.id.tv_eventbus_sticky);
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onMessageEvent(MessageEvent event) {
        LogZ.e( "message is " + event.getMessage());
        // 更新界面
        mTvMessage.setText(event.getMessage());
        // 移除粘性事件
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 注销订阅者
        EventBus.getDefault().unregister(this);
    }
}
