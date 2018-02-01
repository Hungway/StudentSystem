package com.lthergo.studentsystem;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lthergo.studentsystem.StudentADB.StudentDao;
import com.lthergo.studentsystem.StudentADB.StudentInfo;

import java.io.File;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText et_id;
    private EditText et_name;
    private EditText et_phone;
    private ListView lv;
    private StudentDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_deleteall:
                dao.deleteAll();
                Toast.makeText(this,"删除全部数据成功", Toast.LENGTH_SHORT).show();
                lv.setAdapter(new MyAdapter());
                break;
            case R.id.login:
                Toast.makeText(this,"注销成功", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent();
                intent1.setClass(MainActivity.this, LoginActivity.class);
                startActivity(intent1);
                MainActivity.this.finish();
                break;
            case R.id.quit:
                MainActivity.this.finish();
                break;
            case R.id.about:
                Intent intent2 = new Intent();
                intent2.setClass(MainActivity.this, About.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        et_id = (EditText) findViewById(R.id.et_id);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);
        lv = (ListView) findViewById(R.id.lv);
        dao = new StudentDao(this);
        lv.setAdapter(new MyAdapter());
    }

    /**
     * button的点击事件，用来添加学生信息
     * @param view
     */
    public void addStudent(View view) {
        String id =  et_id.getText().toString().trim();
        String name =  et_name.getText().toString().trim();
        String phone =  et_phone.getText().toString().trim();

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) ||TextUtils.isEmpty(phone)) {
            Toast.makeText(this,"数据不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // 保存数据到数据库，并且同步显示到界面
            StudentInfo info = new StudentInfo();
            info.setId(Long.valueOf(id));
            info.setName(name);
            info.setPhone(phone);
            boolean result = dao.add(info);
            if (result) {
                Toast.makeText(this,"添加成功", Toast.LENGTH_SHORT).show();
                lv.setAdapter(new MyAdapter());
            }
        }

    }

    private class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return dao.getTotalCount();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this,R.layout.item,null);
            TextView tv_item_id = (TextView) view.findViewById(R.id.tv_item_id);
            TextView tv_item_name = (TextView) view.findViewById(R.id.tv_item_name);
            TextView tv_item_phone = (TextView) view.findViewById(R.id.tv_item_phone);
            ImageView iv_delete = (ImageView) view.findViewById(R.id.iv_delete);
            final Map<String, String> map = dao.getStudentInfo(position);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean result = dao.delete(map.get("studentid"));
                    if (result) {
                        Toast.makeText(MainActivity.this,"删除成功", Toast.LENGTH_SHORT).show();
                        lv.setAdapter(new MyAdapter());
                    }
                }
            });
            tv_item_id.setText(String.valueOf(map.get("studentid")));
            tv_item_name.setText(map.get("name"));
            tv_item_phone.setText(map.get("phone"));

            return view;
        }

        @Override
        public StudentInfo getItem(int position) {
//            return list.get(position);
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
