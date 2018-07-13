package com.onezao.zao.practices.picgallery0306;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.onezao.zao.zaov.AdminUtils;
import com.onezao.zao.zaov.R;

public class PicGalleryActivity extends Activity {
    private ImageView iv_picgallery_0110;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picgallery_0306);
        iv_picgallery_0110 = (ImageView) findViewById(R.id.iv_picgallery_0110);
    }

    public   void  gallery(View view){
        Intent intent = new Intent();
        intent.setAction("android.intent.action.PICK");
        intent.setType("image/*");
        this.startActivityForResult(intent, 0);
    }

    // 得到返回值，即选中的图片
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Uri uri = data.getData();
            iv_picgallery_0110.setImageURI(uri);
        }
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

        AdminUtils.AdminMenu(PicGalleryActivity.this, item);
        return super.onOptionsItemSelected(item);
    }
}
