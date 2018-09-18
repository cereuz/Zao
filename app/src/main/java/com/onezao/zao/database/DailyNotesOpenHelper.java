package com.onezao.zao.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.onezao.zao.utils.ConstantValue;


public class DailyNotesOpenHelper extends SQLiteOpenHelper {
    public DailyNotesOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //创建数据库中表的方法
        sqLiteDatabase.execSQL(ConstantValue.DATABASE_CREATE_TABLE_DAILYNOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
