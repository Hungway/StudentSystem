package com.lthergo.studentsystem.StudentADB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by musei on 2018/1/1.
 */

public class StudentDao {
    private StudentDBOpenHelper helper;

    public StudentDao(Context context) {
        helper = new StudentDBOpenHelper(context);
    }

    /**
     * 添加一条记录
     *
     * @param Studentid
     *          学生id
     * @param name
     *          学生姓名
     * @param phone
     *          学生电话
     * @return  是否添加成功
     */
    public boolean add(String Studentid, String name, String phone) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("studentid", Studentid);
        values.put("name", name);
        values.put("phone", phone);
        long row = db.insert("info", null, values);
        db.close();
        return row == -1 ? false : true;
    }

    /**
     * 添加一条记录
     *
     * @param info
     *          student domain
     * @return  是否添加成功
     */
    public boolean add(StudentInfo info) {
        return add(String.valueOf(info.getId()), info.getName(), info.getPhone());
    }

    /**
     * 删除一条记录
     *
     * @param Studentid
     *          学生id
     * @return  是否删除成功
     */
    public boolean delete(String Studentid) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int count = db.delete("info", "studentid=?", new String[]{Studentid});
        db.close();
        return count <= 0 ? false : true;
    }

    /**
     * 查询一条记录
     *
     * @param position
     *          数据在数据库表里面的位置
     * @return  一条记录信息
     */
    public Map<String, String> getStudentInfo(int position) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("info", new String[]{"studentid", "name", "phone"}, null, null, null, null, null);
        cursor.moveToPosition(position);
        String studentid = cursor.getString(0);
        String name = cursor.getString(1);
        String phone = cursor.getString(2);
        cursor.close();
        db.close();
        HashMap<String, String> result = new HashMap<>();
        result.put("studentid", studentid);
        result.put("name", name);
        result.put("phone", phone);
        return result;
    }

    /**
     * 查询数据库里面一共有多少条记录
     *
     * @return  记录的条数
     */
    public int getTotalCount() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query("info", null, null, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    /**
     * 删除所有的数据
     */
    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.beginTransaction();// 开启事务

        try {
            Cursor cursor = db.query("info", new String[]{"studentid"}, null, null, null, null, null);
            while (cursor.moveToNext()) {
                String studentid = cursor.getString(0);
                db.delete("info", "studentid=?", new String[]{studentid});
            }
            cursor.close();
            db.setTransactionSuccessful();// 设置事务执行成功，必须要这一行代码执行，数据才会被提交
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}