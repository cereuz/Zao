package com.onezao.zao.javamail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.onezao.zao.zaov.ConstantValue;
import com.onezao.zao.zaov.R;
import com.onezao.zao.zaov.SpUtils;

public class JavaMailSetupActivity extends AppCompatActivity {

    TextView tv_host;
    TextView tv_port;
    TextView tv_from_add;
    TextView tv_from_psw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean mail_setup = SpUtils.getBoolean(this,ConstantValue.Zaov_File,ConstantValue.JAVA_MAIL_SETUP,false);
        if(mail_setup){
           // 跳转到发送邮件页面;
            Intent intent = new Intent(this,JavaMailActivity.class);
            startActivity(intent);
        } else {
            //进入设置页面
            initUI();
        }
    }

    private void initUI() {
        setContentView(R.layout.activity_javamail_setup);
        tv_host = (TextView)findViewById(R.id.tv_host);
        tv_port = (TextView)findViewById(R.id.tv_port);
        tv_from_add = (TextView)findViewById(R.id.tv_from_add);
        tv_from_psw = (TextView)findViewById(R.id.tv_from_psw);
    }

    public void setup(View view) {
        String tvHost = tv_host.getText().toString().trim();
        String tvPort = tv_port.getText().toString().trim();
        String tvFromAdd = tv_from_add.getText().toString().trim();
        String tvFromPwd = tv_from_psw.getText().toString().trim();

        SpUtils.putString(this,ConstantValue.Zaov_File,ConstantValue.TV_HOST,tvHost);
        SpUtils.putString(this,ConstantValue.Zaov_File,ConstantValue.TV_PORT,tvPort);
        SpUtils.putString(this,ConstantValue.Zaov_File,ConstantValue.TV_FROM_ADD,tvFromAdd);
        SpUtils.putString(this,ConstantValue.Zaov_File,ConstantValue.TV_FROM_PWD,tvFromPwd);
        Toast.makeText(this,"设置成功！",Toast.LENGTH_SHORT).show();
        SpUtils.putBoolean(this,ConstantValue.Zaov_File,ConstantValue.JAVA_MAIL_SETUP,true);
        Intent intent = new Intent(this,JavaMailActivity.class);
        startActivity(intent);
        finish();
    }
}
