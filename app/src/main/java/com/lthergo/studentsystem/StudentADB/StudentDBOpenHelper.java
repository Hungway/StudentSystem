package com.lthergo.studentsystem.StudentADB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class StudentDBOpenHelper extends SQLiteOpenHelper {
    public StudentDBOpenHelper(Context context) {
        super(context, "student.db", null, 1);
    }

    // 数据库第一次被创建调用的方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table info (_id integer primary key autoincrement, studentid varchar(20), name varchar(20), phone varchar(20));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}