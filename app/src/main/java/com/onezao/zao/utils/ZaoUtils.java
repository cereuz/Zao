package com.onezao.zao.utils;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.onezao.zao.database.DailyNotesDao;
import com.onezao.zao.practices.myzxing0306.utils.Constant;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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


    //获取系统时间。
    @SuppressLint("SimpleDateFormat")
    public static String getSystemTimeMore(int i) {
        SimpleDateFormat mFormat = null;
        Date date = new Date(System.currentTimeMillis());//获取当前时间
        switch (i) {
            case 1:
                mFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒SS毫秒 EEEE");
                //2018年07月24日 15时16分27秒 星期二
                Log.e("CurTime", "time1=" + mFormat.format(date));
                break;

            case 2:
                mFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.ENGLISH);
                Log.e("CurTime", "time2=" + mFormat.format(date));
                break;
            case 3:
                mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Log.e("CurTime", "time2=" + mFormat.format(date));
                break;
            case 4:
                mFormat = new SimpleDateFormat("yyyy/MM/dd");
                Log.e("CurTime", "time3=" + mFormat.format(date));
                break;
            case 5:
                mFormat = new SimpleDateFormat("HH-mm-ss");
                Log.e("CurTime", "time4=" + mFormat.format(date));
                break;
            case 6:
                mFormat = new SimpleDateFormat("EEEE");
                Log.e("CurTime", "time5=" + mFormat.format(date));
                break;
            case 7:
                mFormat = new SimpleDateFormat("E");
                Log.e("CurTime", "time6=" + mFormat.format(date));
                break;

            case 8:
                mFormat = new SimpleDateFormat("yyyy年MM月dd日 ，EEEE ");
                break;

            case 9:
                SimpleDateFormat sdf2 = new SimpleDateFormat("MM dd, yyyy HH:mm:ss",
                        Locale.ENGLISH);
                break;
        }

        //	SimpleDateFormat    formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日 HH:mm:ss ");
//        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy年MM月dd日 , EEEE ");
        String str = mFormat.format(date);
        return str;
    }

        //从数据库文件夹复制文件到SD卡
        public static void copyDBtoSD(Context context) {
            //黑名单的数据库
            String pathDB = context.getDatabasePath(ConstantValue.DADABASE_DAILYNOTES).getAbsolutePath();
            ZaoUtils.copyFile(pathDB, ZaoUtils.pathSD + "/ame/DailyNotes.db");
        }
        
        
        public static void addDailyNotesData(final Context context){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DailyNotesDao mDao = DailyNotesDao.getInstance(context);
                    for(int i = 0 ; i < 100 ; i++){
                        String title;
                        String author ;
                        String content;
                        String picpath;
                        String email ;
                        String time;
                        if(i < 10){
                            title = "1328238000" + i;
                            author = "蜗牛" + i;
                            content = ConstantValue.AUTHORIZATION;
                            picpath = "https://img3.doubanio.com/view/status/m/public/3c0f4bfec96a30e.webp";
                            email = "sune"+ i +"do@qq.com" ;
                            time = ZaoUtils.getSystemTimeMore(1);
                            //往数据库插入数据
                            mDao.insert(title, author,content,picpath,email,time);
                            ZaoUtils.sleep(20);
                        }  else if(i < 100){
                            title = "132823800" + i;
                            author = "蜗牛" + i;
                            content = ConstantValue.AUTHORIZATION;
                            picpath = "https://img3.doubanio.com/view/status/m/public/3c0f4bfec96a30e.webp";
                            email = "sune"+ i +"do@qq.com" ;
                            time = ZaoUtils.getSystemTimeMore(1);
                            //往数据库插入数据
                            mDao.insert(title, author,content,picpath,email,time);
                            //睡眠，
                            ZaoUtils.sleep(20);
                        }  else if (i < 1000){
                            title = "132823800" + i;
                            author = "蜗牛" + i;
                            content = ConstantValue.AUTHORIZATION;
                            picpath = "https://img3.doubanio.com/view/status/m/public/3c0f4bfec96a30e.webp";
                            email = "sune"+ i +"do@qq.com" ;
                            time = ZaoUtils.getSystemTimeMore(1);
                            //往数据库插入数据
                            mDao.insert(title, author,content,picpath,email,time);
                            ZaoUtils.sleep(10);
                        }
                    }

                    //黑名单的数据库
                    ZaoUtils.copyDBtoSD(context);
                }
            }).start();

        }
}

