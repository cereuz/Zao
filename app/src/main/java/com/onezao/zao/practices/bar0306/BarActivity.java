package com.onezao.zao.practices.bar0306;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.onezao.zao.zaov.R;

/**
 * @author : zw
 * @email : zsky@live.com
 * @motto : To be, or not to be.
 * @date : 2019/3/23 10:28
 */
public class BarActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        setContentView(R.layout.toolbar_layout);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //透明导航栏
        Toolbar mToolbar = (Toolbar) findViewById(R.id.bar_toolbar);
        mToolbar.setTitle("App Title"); //设置Toolbar标题
        mToolbar.setSubtitle("Sub Title"); //设置Toolbar 副标题
/*        mToolbar.setLogo(R.mipmap.ic_tab_status_active);//设置Toolbar的Logo
        mToolbar.setNavigationIcon(R.mipmap.ic_tab_status_normal);*/
        setSupportActionBar(mToolbar);
    }
}
