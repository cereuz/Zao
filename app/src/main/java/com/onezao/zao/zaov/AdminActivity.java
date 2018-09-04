package com.onezao.zao.zaov;

import android.Manifest;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import android.widget.Toast;

import com.onezao.zao.gridrecycleview0306.GridRecycleActivity;
import com.onezao.zao.practices.alertdialog0306.AlertDialogActivity;

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

    //动态申请权限
    private static final int MY_PERMISSION_REQUEST_CODE = 10000;
    final String positive = "获取权限" ;
    final String negative = "退出";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * 点击桌面图标，重新进入，不会重启
         */
        if (!isTaskRoot()) {
            finish();
            return;
        }

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
        initPermission();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            checkPermission();
        } else {
            return;
        }
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


    public void checkPermission() {
        /**
         * 第 1 步: 检查是否有相应的权限
         */
        boolean isAllGranted = checkPermissionAllGranted(
                new String[] {
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.CAMERA,

                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
/*                        Manifest.permission.FLASHLIGHT,
                        Manifest.permission.READ_HISTORY_BOOKMARKS,*/
//                        Manifest.permission.CAMERA
                }
        );
        // 如果这3个权限全都拥有, 则直接执行读取短信代码
        if (isAllGranted) {
/*            getData();
            simpleAdapter.notifyDataSetChanged();*/
            toast("所有权限已经授权！");
            return;
        }

        /**
         * 第 2 步: 请求权限
         */
        // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
        ActivityCompat.requestPermissions(
                this,
                new String[] {
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CHANGE_WIFI_STATE,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        Manifest.permission.ACCESS_NETWORK_STATE,
//                        Manifest.permission.CAMERA
                },
                MY_PERMISSION_REQUEST_CODE
        );
    }
    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                toast("检查权限");
                return false;
            }
        }
        return true;
    }

    /**
     * 第 3 步: 申请权限结果返回处理
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isAllGranted = true;

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                    break;
                }
            }

            if (isAllGranted) {
                // 如果所有的权限都授予了, 则执行读取短信代码
/*                getData();
                simpleAdapter.notifyDataSetChanged();*/
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
//                openAppDetails();
                toast("需要授权！");
                myPermissionDialog();
            }
        }
    }
    public void toast(String content){
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }

    //点击按钮，弹出一个普通对话框
    public void myPermissionDialog(){
        // 通过builder 构建器来构造
        final AlertDialog.Builder   builder =  new AlertDialog.Builder(this);
        builder.setTitle("警告zz");
        builder.setMessage("世界上最遥远的距离是没有网络！");
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("点击了确定按钮");
                Toast.makeText(AdminActivity.this, positive, Toast.LENGTH_SHORT).show();
                initPermission();
            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("点击了取消按钮");
                Toast.makeText(AdminActivity.this, negative, Toast.LENGTH_SHORT).show();
                AdminActivity.this.finish();
            }
        });
        builder.setCancelable(false);  //设置获取权限框不可以取消。
        //最后一步，一定要记得和Toast一样，show出来。
        builder.show();
    }
}
