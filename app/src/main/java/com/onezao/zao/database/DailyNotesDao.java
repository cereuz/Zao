package com.onezao.zao.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.onezao.zao.bean.DailyNotesInfo;
import com.onezao.zao.utils.ConstantValue;

import java.util.ArrayList;
import java.util.List;

public class DailyNotesDao {

       DailyNotesOpenHelper mOpenHelper;
       SQLiteDatabase db;

    //BlackNumberDao 单例模式
    //1.私有化构造方法
    private DailyNotesDao(Context context){
        //创建数据库以及其表结构
        mOpenHelper = new DailyNotesOpenHelper(context, ConstantValue.DADABASE_DAILYNOTES,1);
    };
    //2.声明一个当前类的对象
    private static DailyNotesDao mDao = null;
    //3.提供一个方法，如果当前类的对象为空，创建一个新的
    public static DailyNotesDao getInstance(Context context){
        if(mDao == null){
            mDao = new DailyNotesDao(context);
        }
        return mDao;
    }

    /**
     * 往数据库中插入数据的操作
     * @param title
     * @param author
     * @param content
     * @param picpath
     * @param email
     * @param time
     */
    public void insert(String title, String author, String content, String picpath, String email, String time){
        //1.开启数据库，准备做写入操作
        db = mOpenHelper.getWritableDatabase();
        //2.插入数据
        ContentValues values = new ContentValues();
        values.put("title",title);
        values.put("author",author);
        values.put("content",content);
        values.put("picpath",picpath);
        values.put("email",email);
        values.put("time",time);
        db.insert(ConstantValue.DATABASE_DAILYNOTES_TABLE_NAME,null,values);
        db.close();
    }

    /**
     * 从数据库中删除一条数据
     * @param title  删除对应标题的数据
     */
    public void delete(String title){
       db = mOpenHelper.getWritableDatabase();
       db.delete(ConstantValue.DATABASE_DAILYNOTES_TABLE_NAME,"title = ?",new String[]{title});
       db.close();
    }

    /**
     * 根据标题  来更新数据
     * @param oldTitle
     * @param newTitle
     * @param author
     * @param content
     * @param picpath
     * @param email
     * @param time
     */
    public void update(String oldTitle, String newTitle, String author, String content, String picpath, String email, String time){
        db = mOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("title",newTitle);
        values.put("author",author);
        values.put("content",content);
        values.put("picpath",picpath);
        values.put("email",email);
        values.put("time",time);
        db.update(ConstantValue.DATABASE_DAILYNOTES_TABLE_NAME,values,"bn_phone = ? ",new String[]{oldTitle});

        db.close();
    }

    /**
     * ANDROID_HOME
     *  查询所有存储的数据，并返回
     * @return   返回list集合  List<BlackNumberInfo>
     */
    public List<DailyNotesInfo> findAll(){
        db = mOpenHelper.getWritableDatabase();
        Cursor cursor = db.query(ConstantValue.DATABASE_DAILYNOTES_TABLE_NAME,new String[]{"title","author","content","picpath","email","time"},null,null,null,null,"_id desc");
            //创建存储查询到的数据的集合
        List<DailyNotesInfo> list = new ArrayList<DailyNotesInfo>();
        while (cursor.moveToNext()){
            //创建每一条数据的对象
            DailyNotesInfo mDialyNotesInfo = new DailyNotesInfo();
            mDialyNotesInfo.setTitle(cursor.getString(0));
            mDialyNotesInfo.setAuthor(cursor.getString(1));
            mDialyNotesInfo.setContent(cursor.getString(2));
            mDialyNotesInfo.setPicpath(cursor.getString(3));
            mDialyNotesInfo.setEmail(cursor.getString(4));
            mDialyNotesInfo.setTime(cursor.getString(5));
            list.add(mDialyNotesInfo);
        }
             cursor.close();
             db.close();

            return list;
    }

    /**
     * ANDROID_HOME
     *  查询所有存储的数据，并返回
     * @return   返回list集合  List<BlackNumberInfo>
     */
    public List<DailyNotesInfo> find(int index){
        db = mOpenHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select title,author,content,picpath,email,time from dailynotes order by _id desc limit ?,10",new String[]{index + ""});
        //创建存储查询到的数据的集合
        List<DailyNotesInfo> list = new ArrayList<DailyNotesInfo>();
        while (cursor.moveToNext()){
            //创建每一条数据的对象
            DailyNotesInfo mDialyNotesInfo = new DailyNotesInfo();
            mDialyNotesInfo.setTitle(cursor.getString(0));
            mDialyNotesInfo.setAuthor(cursor.getString(1));
            mDialyNotesInfo.setContent(cursor.getString(2));
            mDialyNotesInfo.setPicpath(cursor.getString(3));
            mDialyNotesInfo.setEmail(cursor.getString(4));
            mDialyNotesInfo.setTime(cursor.getString(5));
            list.add(mDialyNotesInfo);
        }
        cursor.close();
        db.close();

        return list;
    }
    
    
    
    /**
     * 查询黑名单总数
     * @return 黑名单的总数
     */
    public int queryTotal() {
        // 创建数据，每个方法单独创建数据库，以便每次用完数据库后关闭
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        // 查询数据库
        Cursor query = db.rawQuery("select count(*) from " + ConstantValue.DATABASE_DAILYNOTES_TABLE_NAME, null);
        int count = 0;
        if (query.moveToNext()) {
            //得到总数
            count = query.getInt(0);
        }
        //关闭数据库
        db.close();
        return count;
    }
}
