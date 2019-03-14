package com.onezao.zao.practices.eventbus0306.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.onezao.zao.practices.eventbus0306.MessageEvent;
import com.onezao.zao.zaov.R;

import org.greenrobot.eventbus.EventBus;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/14 11:15
 */
public class EventBusOrderSecondActivity extends AppCompatActivity implements View.OnClickListener {

        public static void start(Context context) {
            Intent intent = new Intent(context, EventBusOrderSecondActivity.class);
            context.startActivity(intent);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_eventbus_order_view);
            initContentView();
        }

        private void initContentView() {
            Button btn_order = (Button)findViewById(R.id.btn_order_start_activity);
            btn_order.setText("EventBusOrderSecondActivity");
            btn_order.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_order_start_activity) {
                // 发布事件
                EventBus.getDefault().post(new MessageEvent("Hello EventBus!"));
            }
        }
    }