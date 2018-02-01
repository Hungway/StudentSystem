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

import java.util.Map;

public class DisplayActivity extends AppCompatActivity {
    //private EditText et_id;
    //private EditText et_name;
    //private EditText et_phone;
    private ListView lv;
    private StudentDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login3:
                Toast.makeText(this,"注销成功", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent();
                intent1.setClass(DisplayActivity.this, LoginActivity.class);
                startActivity(intent1);
                DisplayActivity.this.finish();
                break;
            case R.id.quit3:
                DisplayActivity.this.finish();
                break;
            case R.id.about3:
                Intent intent2 = new Intent();
                intent2.setClass(DisplayActivity.this, About.class);
                startActivity(intent2);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void initView() {
        setContentView(R.layout.activity_display);
   /*     et_id = (EditText) findViewById(R.id.et_id);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phone = (EditText) findViewById(R.id.et_phone);*/
        lv = (ListView) findViewById(R.id.lv);
        dao = new StudentDao(this);
        lv.setAdapter(new MyAdapter());
    }

  /*  public void addStudent(View view) {
        String id =  et_id.getText().toString().trim();
        String name =  et_name.getText().toString().trim();
        String phone =  et_phone.getText().toString().trim();

        if (TextUtils.isEmpty(id) || TextUtils.isEmpty(name) ||TextUtils.isEmpty(phone)) {
            Toast.makeText(this,"数据不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            // 保存数据到数据库，并且同步显示到界面
            StudentInfo info = new StudentInfo();
            info.setId(Integer.valueOf(id));
            info.setName(name);
            info.setPhone(phone);
            boolean result = dao.add(info);
            if (result) {
                Toast.makeText(this,"添加成功", Toast.LENGTH_SHORT).show();
                lv.setAdapter(new MyAdapter());
            }
        }

    }*/

    private class MyAdapter extends BaseAdapter {
        // @Override
        public int getCount() {
            return dao.getTotalCount();
        }
        //@Override
       public View getView(final int position, View convertView, ViewGroup parent) {

            View view = View.inflate(DisplayActivity.this,R.layout.item_2,null);
            TextView tv_item_id = view.findViewById(R.id.tv_item_id_2);
            TextView tv_item_name = view.findViewById(R.id.tv_item_name_2);
            TextView tv_item_phone = view.findViewById(R.id.tv_item_phone_2);
            final Map<String, String> map = dao.getStudentInfo(position);
            tv_item_id.setText(String.valueOf(map.get("studentid")));
            tv_item_name.setText(map.get("name"));
            tv_item_phone.setText(map.get("phone"));

            return view;
        }

        @Override
        public StudentInfo getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
    }
}
