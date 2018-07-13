package com.onezao.zao.practices.sendbroadcast0306;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.onezao.zao.zaov.AdminUtils;
import com.onezao.zao.zaov.R;

public class SendBroadcastActivity extends Activity implements View.OnClickListener{
    private Button btn_sendbroadcast;
    private EditText et_action;
    private EditText et_sendbroadcast_key;
    private EditText et_sendbroadcast_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendbroadcast_0306);

        et_action = (EditText) findViewById(R.id.et_action);
        et_sendbroadcast_key = (EditText) findViewById(R.id.et_sendbroadcast_key);
        et_sendbroadcast_value = (EditText) findViewById(R.id.et_sendbroadcast_value);

        btn_sendbroadcast = (Button) findViewById(R.id.btn_sendbroadcast);
        btn_sendbroadcast.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        String action = et_action.getText().toString().trim();
        String sendbroadcast_key = et_sendbroadcast_key.getText().toString().trim();
        String sendbroadcast_value = et_sendbroadcast_value.getText().toString().trim();
        Intent intent = new Intent();
        intent.setAction(action);
        //发送数据
        intent.putExtra(sendbroadcast_key,sendbroadcast_value);
        sendBroadcast(intent);
    }


    //加载顶部菜单，添加菜单的点击事件。
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //设置左上角的图标的点击事件  ActionBar
        ActionBar actionBar = this.getActionBar();
        actionBar.setHomeButtonEnabled(true);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AdminUtils.AdminMenu(SendBroadcastActivity.this, item);
        return super.onOptionsItemSelected(item);
    }
}

