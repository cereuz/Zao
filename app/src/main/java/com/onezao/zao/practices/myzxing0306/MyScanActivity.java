package com.onezao.zao.practices.myzxing0306;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.onezao.zao.practices.myzxing0306.utils.Constant;
import com.onezao.zao.zaov.R;

public class MyScanActivity extends Activity implements View.OnClickListener{

    Button create_code;
    Button scan_2code;
    Button scan_bar_code;
    Button scan_code;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scan);
        int mode = getIntent().getIntExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);

        create_code =  (Button)findViewById(R.id.create_code);
        create_code.setOnClickListener(this);
        scan_2code =  (Button)findViewById(R.id.scan_2code);
        scan_2code.setOnClickListener(this);
        scan_bar_code =  (Button)findViewById(R.id.scan_bar_code);
        scan_bar_code.setOnClickListener(this);
        scan_code =  (Button)findViewById(R.id.scan_code);
        scan_code.setOnClickListener(this);

    }

    /**
     * 按钮监听事件，这里我使用Butterknife，不喜欢的也可以直接写监听
     * @param view
     */
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case  R.id.create_code: //生成码
                intent=new Intent(this,CreateCodeActivity.class);
                startActivity(intent);
                break;
            case  R.id.scan_2code: //扫描二维码
                intent=new Intent(this,CommonScanActivity.class);
                intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_QRCODE_MODE);
                startActivity(intent);
                break;
            case  R.id.scan_bar_code://扫描条形码
                intent=new Intent(this,CommonScanActivity.class);
                intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_BARCODE_MODE);
                startActivity(intent);
                break;
            case  R.id.scan_code://扫描条形码或者二维码
                intent=new Intent(this,CommonScanActivity.class);
                intent.putExtra(Constant.REQUEST_SCAN_MODE,Constant.REQUEST_SCAN_MODE_ALL_MODE);
                startActivity(intent);
                break;
        }
    }
}

