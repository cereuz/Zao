package com.onezao.zao.zaov;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class AdminActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    RelativeLayout rl_home;
    RelativeLayout rl_status;
    RelativeLayout rl_group;
    RelativeLayout rl_profile;

    //设置修改图片
    ImageView iv_home;
    ImageView iv_status;
    ImageView iv_group;
    ImageView iv_profile;

    //设置修改文字
    TextView  tv_onezao_home;
    TextView  tv_onezao_status;
    TextView  tv_onezao_group;
    TextView  tv_onezao_profile;

    //初始化四个Fragment
    private HomeFragment   homeFragment;
    private StatusFragment   statusFragment;
    private GroupFragment   groupFragment;
    private ProfileFragment   profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        initView();
        initHome();
    }

    private void initView() {
        rl_home = (RelativeLayout) findViewById(R.id.rl_home);
        rl_home.setOnClickListener(this);
        rl_status = (RelativeLayout) findViewById(R.id.rl_status);
        rl_status.setOnClickListener(this);
        rl_group = (RelativeLayout) findViewById(R.id.rl_group);
        rl_group.setOnClickListener(this);
        rl_profile = (RelativeLayout) findViewById(R.id.rl_profile);
        rl_profile.setOnClickListener(this);

        iv_home = (ImageView) findViewById(R.id.iv_home);
        iv_status = (ImageView) findViewById(R.id.iv_status);
        iv_group = (ImageView) findViewById(R.id.iv_group);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);

        tv_onezao_home = (TextView) findViewById(R.id.tv_onezao_home);
        tv_onezao_status = (TextView) findViewById(R.id.tv_onezao_status);
        tv_onezao_group = (TextView) findViewById(R.id.tv_onezao_group);
        tv_onezao_profile = (TextView) findViewById(R.id.tv_onezao_profile);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //控制侧滑栏目
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //初始化首页数据
    private void initHome() {
        //获取fragment  管理者
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if(homeFragment  ==  null){
            homeFragment = new  HomeFragment();
        }
        transaction.replace(R.id.fragment_onezao_0403, homeFragment);
        //将四个的图片全部设置为未点击状态。
        clearIcon();
        iv_home.setImageResource(R.mipmap.ic_tab_home_active);
        tv_onezao_home.setTextColor(getResources().getColor(R.color.colorGreen));
        transaction.commit();
    }


    //加载顶部菜单，添加菜单的点击事件。
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
/*        //设置左上角的图标的点击事件  ActionBar
        ActionBar actionBar = this.getActionBar();
        actionBar.setHomeButtonEnabled(true);*/
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        AdminUtils.AdminMenu(AdminActivity.this, item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        //获取fragment  管理者
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (view.getId()){
            case R.id.rl_home :
                if(homeFragment  ==  null){
                    homeFragment = new  HomeFragment();
                }
                transaction.replace(R.id.fragment_onezao_0403, homeFragment);
                //将四个的图片全部设置为未点击状态。
                clearIcon();
                iv_home.setImageResource(R.mipmap.ic_tab_home_active);
                tv_onezao_home.setTextColor(getResources().getColor(R.color.colorGreen));
                break;

            case R.id.rl_status :
                if(statusFragment  ==  null){
                    statusFragment = new  StatusFragment();
                }
                transaction.replace(R.id.fragment_onezao_0403, statusFragment);
                //将四个的图片全部设置为未点击状态。
                clearIcon();
                iv_status.setImageResource(R.mipmap.ic_tab_status_active);
                tv_onezao_status.setTextColor(getResources().getColor(R.color.colorGreen));
                break;

            case R.id.rl_group :
                if(groupFragment  ==  null){
                    groupFragment = new  GroupFragment();
                }
                transaction.replace(R.id.fragment_onezao_0403, groupFragment);
                //将四个的图片全部设置为未点击状态。
                clearIcon();
                iv_group.setImageResource(R.mipmap.ic_tab_group_active);
                tv_onezao_group.setTextColor(getResources().getColor(R.color.colorGreen));
                break;

            case R.id.rl_profile :
                if(profileFragment  ==  null){
                    profileFragment = new  ProfileFragment();
                }
                transaction.replace(R.id.fragment_onezao_0403, profileFragment);
                //将四个的图片全部设置为未点击状态。
                clearIcon();
                iv_profile.setImageResource(R.mipmap.ic_tab_profile_active);
                tv_onezao_profile.setTextColor(getResources().getColor(R.color.colorGreen));
                break;
        }
        transaction.commit();
    }

    private  void  clearIcon(){
        iv_home.setImageResource(R.mipmap.ic_tab_home_normal);
        iv_status.setImageResource(R.mipmap.ic_tab_status_normal);
        iv_group.setImageResource(R.mipmap.ic_tab_group_normal);
        iv_profile.setImageResource(R.mipmap.ic_tab_profile_normal);
        tv_onezao_home.setTextColor(getResources().getColor(R.color.colorText));
        tv_onezao_status.setTextColor(getResources().getColor(R.color.colorText));
        tv_onezao_group.setTextColor(getResources().getColor(R.color.colorText));
        tv_onezao_profile.setTextColor(getResources().getColor(R.color.colorText));
    }

}
