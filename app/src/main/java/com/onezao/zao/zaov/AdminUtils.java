package com.onezao.zao.zaov;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;
import android.widget.Toast;

import com.onezao.zao.practices.screenrecord0306.ScreenRecordActivity;
import com.onezao.zao.recycleview0306.RecycleViewActivity;
import com.onezao.zao.utils.ZaoUtils;

/**
 * Created by Administrator on 2018/4/2 0002.
 */

public class AdminUtils {
    public static boolean AdminMenu(Context context , MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_diary) {
            Toast.makeText(context, "日记。。。", Toast.LENGTH_SHORT).show();
 /*			Intent  intent  =  new  Intent(this,WritediaryActivity.class);
 			startActivity(intent);*/
            return true;
        }  else  if (id == R.id.action_query) {
            Toast.makeText(context, "查询。。。", Toast.LENGTH_SHORT).show();
/* 			Intent  intent  =  new Intent(context,TestMeActivity.class);
 			context.startActivity(intent);*/
            Intent intent = new Intent(context,RecycleViewActivity.class);
            context.startActivity(intent);
            return true;
        }  else  if (id == R.id.action_settings) {
            Toast.makeText(context, "设置。。。", Toast.LENGTH_SHORT).show();
 /*			Intent  intent  =  new  Intent(this,SettingActivity.class);
 			startActivity(intent);*/
            return true;
        }  else  if (id == R.id.action_about) {
            Toast.makeText(context, "关于。。。", Toast.LENGTH_SHORT).show();
 /*	    Intent  intent  =  new  Intent(this,AboutActivity.class);
 			startActivity(intent);*/
            return true;
        }  else  if (id == android.R.id.home) {
            Toast.makeText(context, "主页,显示全部", Toast.LENGTH_SHORT).show();
 			/*Intent  intent  =  new  Intent(this,AboutActivity.class);
 			startActivity(intent);*/
            //finish()方法目前是在Activity才有的方法，需要使用Actitvity强转一下。
            ((Activity)context).finish();
            return true;
        }  else  if (id ==  R.id.action_search) {
            Toast.makeText(context, "搜索一下，onezao，去除重复", Toast.LENGTH_SHORT).show();
            return true;
        }  else  if (id ==  R.id.action_search2) {
            Toast.makeText(context, "自动添加测试数据", Toast.LENGTH_SHORT).show();
            ZaoUtils.addDailyNotesData(context);
            return true;
        }
        return false;
    }
}
