package com.onezao.zao.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

public class AppVersionUtil {


    /**
     * 获取应用图标
     * @param context
     * @return
     */
    public static Drawable getIcon(Context context) {
        ApplicationInfo appInfo;
        Drawable appIcon;
        String label;

        PackageManager pm = context.getPackageManager();
        try {
            appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);

            // 应用名称
           label  =  pm.getApplicationLabel(appInfo).toString();

           //应用图标
            appIcon = pm.getApplicationIcon(appInfo);
            return appIcon;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取应用名称
     * @param context
     * @return
     */
    public static String getLabel(Context context) {
        ApplicationInfo appInfo;
        String label;

        PackageManager pm = context.getPackageManager();
        try {
            appInfo = pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            // 应用名称
            label  =  pm.getApplicationLabel(appInfo).toString();
            return label;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取版本名
     *
     * @param context
     * @return 获取失败则返回null
     */
    public static String getVersionName(Context context) {
        // 包管理者
        PackageManager mg = context.getPackageManager();
        try {
            // getPackageInfo(packageName 包名, flags 标志位（表示要获取什么数据）);
            // 0表示获取基本数据
            PackageInfo info = mg.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 获取版本号
     *
     * @param context
     * @return 获取失败则返回0
     */
    public static int getVersionCode(Context context) {
        // 包管理者
        PackageManager mg = context.getPackageManager();
        try {
            // getPackageInfo(packageName 包名, flags 标志位（表示要获取什么数据）);
            // 0表示获取基本数据
            PackageInfo info = mg.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
