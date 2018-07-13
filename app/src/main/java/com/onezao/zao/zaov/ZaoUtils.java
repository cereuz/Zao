package com.onezao.zao.zaov;


import android.annotation.SuppressLint;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 蜗牛
 *  pathSD  :   获取SD卡路径
 */
public class ZaoUtils {
    public static String ONEZAO = "onezao";
    public static String loginToast = "用户名或者密码不能为空！";
    public static String loginToast2 = "登录成功！";
    public static String saveSucc = "保存成功！";
    public static String selectCB = "没有勾选CheckBox哦";
    public static String loginToastSave = "正在保存用户名和密码！";
    public static String loginToastSaveSucc = "保存用户名和密码成功！";
    public static String loginToastSaveFail = "保存用户名和密码失败！";
    public static  String  pathSD = Environment.getExternalStorageDirectory().getPath();
    public static String SELECTGENDERANDNAME = "请输入姓名并选择性别！";


    //获取系统时间。
    @SuppressLint("SimpleDateFormat")
    public  static String getSystemTime(){
        //	SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日 HH:mm:ss ");
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm:ss ");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str;
    }

    //时间转换
    public static String  tranTime(String    time){
        SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm:ss ");
        Date    curDate    =   new    Date(Long.valueOf(time));//获取当前时间
        String    str    =    formatter.format(curDate);
        return str;
    }

    //复制文件
    public static void copyFile(String path1,String path2) {
        // TODO Auto-generated method stub
        //封装数据源
        FileInputStream fis;
        try {
            fis = new FileInputStream(new File(path1));
            //封装目的地
            FileOutputStream fos = new FileOutputStream(new File(path2));

            int by = 0;
            while((by = fis.read()) != -1){
                fos.write(by);
            }
            //释放资源(先关谁都行)
            fos.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //读取文件
    public static String readFile(String  path){
        File  file =  new  File(path);
        try {
            BufferedReader bf = new  BufferedReader(new FileReader(file));
            String  content  =  "";
            StringBuilder  sb =  new  StringBuilder();
            while(content != null){
                content = bf.readLine();
                if(content == null){
                    break;
                }
                sb.append(content.trim());
            }
            bf.close();
            return sb.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return  null;
        }
    }

    //睡眠
    public static void  sleep(long  time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

