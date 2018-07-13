package com.onezao.zao.practices.alertdialog0306;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.onezao.zao.gridrecycleview0306.GridRecycleActivity;
import com.onezao.zao.zaov.AdminUtils;
import com.onezao.zao.zaov.R;

public class AlertDialogActivity extends Activity {
    final String positive = "确定" ;
    final String negative = "取消";
    private TextView tv_mydialog_textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog_0306);
        tv_mydialog_textview = (TextView) findViewById(R.id.tv_mydialog_textview);
    }

    //点击按钮，弹出一个普通对话框
    public void clickpt(View view){
        // 通过builder 构建器来构造
        AlertDialog.Builder   builder =  new AlertDialog.Builder(this);
        builder.setTitle("警告zz");
        builder.setMessage("世界上最遥远的距离是没有网络！");
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("点击了确定按钮");
                Toast.makeText(AlertDialogActivity.this, positive, Toast.LENGTH_SHORT).show();
                tv_mydialog_textview.setText(positive);
                Intent intent = new Intent(AlertDialogActivity.this,GridRecycleActivity.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("点击了取消按钮");
                Toast.makeText(AlertDialogActivity.this, negative, Toast.LENGTH_SHORT).show();
                tv_mydialog_textview.setText(negative);
            }
        });
        //最后一步，一定要记得和Toast一样，show出来。
        builder.show();
    }

    //点击按钮，弹出一个单选对话框
    public void clickdx(View  view){
        // 通过builder 构建器来构造
        AlertDialog.Builder   builder =  new AlertDialog.Builder(this);
        builder.setTitle("请选择你喜欢的水果(单选)。");
        final String items[] = {"苹果","香蕉","雪梨","菠萝","蜜桃","红枣","Android","ios","c","C++","html","c#","php"};
        // 单选是设置单选按钮选项  .   -1代表没有条目被选中
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //[1] 把选择的条目给取出来
                String item = items[which];
                Toast.makeText(AlertDialogActivity.this, item, Toast.LENGTH_SHORT).show();
                tv_mydialog_textview.setText(item);
                //[2] 把对话框关闭
                dialog.dismiss();
            }
        });
        //最后一步，一定要记得和Toast一样，show出来。
        builder.show();
    }

    //点击按钮，弹出一个多选对话框
    public void clickdxs(View  view){
        // 通过builder 构建器来构造
        AlertDialog.Builder   builder =  new AlertDialog.Builder(this);
        builder.setTitle("请选择你喜欢的水果(多选)。");
        final String items[] = {"苹果","香蕉","雪梨","菠萝","蜜桃","红枣","Android","ios","c","C++","html","c#","php"};
        //默认选中的选项
        final boolean[ ]  checkedItems={false,true,false,false,true,true,false,false,false,true,false,false,false};
        //多选按钮的点击事件
        builder.setMultiChoiceItems(items, checkedItems, new  DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //把选中的条目的数据给我取出来
                StringBuffer  sb = new StringBuffer();
                for (int i = 0; i < checkedItems.length; i++) {
                    //判断一下选中的
                    if(checkedItems[i]){
                        String  fruit = items[i];
                        sb.append(fruit + "    ");
                    }
                }
                Toast.makeText(AlertDialogActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                tv_mydialog_textview.setText(sb.toString());
                //关闭对话框
                dialog.dismiss();
            }
        });
        //最后一步，一定要记得和Toast一样，show出来。
        builder.show();
    }


    //与进度相关的都可以直接在子线程更新UI
    //点击按钮 弹出一个进度条对话框
    public void clickjd(View  view){
        //与进度相关的都可以直接在子线程更新UI
        //使用ProgressDialog构造
        final ProgressDialog dialog = new  ProgressDialog(this);
        dialog.setTitle("正在玩命加载中");
        //可以设置一下进度条的样式
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //最后一步，一定要记得和Toast一样，show出来。
        dialog.show();
        //创建一个子线程
        new Thread(){public void run(){
            //设置进度条最大值
            dialog.setMax(100);
            //设置当前进度
            for(int i = 0;i < 100 ;i++){
                dialog.setProgress(i);
                //睡眠一会
                SystemClock.sleep(100);
            }
            //关闭对话框
            dialog.dismiss();
        };
        }.start();
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

        AdminUtils.AdminMenu(AlertDialogActivity.this, item);
        return super.onOptionsItemSelected(item);
    }
}