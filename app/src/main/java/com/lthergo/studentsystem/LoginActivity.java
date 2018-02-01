package com.lthergo.studentsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText qq;
    private EditText pwd;
    private CheckBox cb;
    private Button btn;
    private SharedPreferences sp;   //定义一个共享参数sharedpreferences保存记住的密码数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = this.getSharedPreferences("config", MODE_PRIVATE);//生成的config.xml文件保存账号密码
        qq = (EditText) findViewById(R.id.qq);
        pwd = (EditText) findViewById(R.id.pwd);
        cb = (CheckBox) findViewById(R.id.checkBox);
        btn = (Button) findViewById(R.id.button);
        restoreInfo();

        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //如果选中，显示密码
                    pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //否则隐藏密码
                    pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

            }
        });

        btn.setOnClickListener(new Button.OnClickListener() {
            @Override

            public void onClick(View v) {
                String qqnumber = qq.getText().toString();
                String password = pwd.getText().toString();

                if (qqnumber.equals("admin") && password.equals("123456")) {
                    Toast.makeText(getBaseContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else if(qqnumber.equals("student") && password.equals("123456")) {
                    Toast.makeText(getBaseContext(), "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, DisplayActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                } else
                    Toast.makeText(getBaseContext(), "账号或密码错误", Toast.LENGTH_SHORT).show();
            }
        });

/*        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sp.edit();//把用户名和密码文件分开
                if (isChecked) {
                    String qqnumber = qq.getText().toString();
                    String password = pwd.getText().toString();
                    // 被选中状态，需要记录用户名和密码
                    // 3.将数据保存到sp文件中
                    editor.putString("qqnumber", qqnumber);
                    editor.putString("password", password);
                    //cb2.setChecked(true);
                    editor.apply();// 提交数据
                } else {
                    String qqnumber = qq.getText().toString();
                    String password = pwd.getText().toString();
                    editor.putString("qqnumber", null);
                    editor.putString("password", null);
                    editor.apply();
                }//取消记住密码需要点击了两次，因为我不知道怎么设置默认为记住密码checked

            }
        });*/
    }

    private void restoreInfo() {
        String qqnumber = sp.getString("qqnumber", "");
        String password = sp.getString("password", "");
        qq.setText(qqnumber);
        pwd.setText(password);//从保存的sp文件里读取信息
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_login, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = sp.edit();
        switch (item.getItemId()) {
            case R.id.savepwd:
                String qqnumber = qq.getText().toString();
                String password = pwd.getText().toString();
                // 被选中状态，需要记录用户名和密码
                // 3.将数据保存到sp文件中
                editor.putString("qqnumber", qqnumber);
                editor.putString("password", password);
                //cb2.setChecked(true);
                editor.apply();// 提交数据
                Toast.makeText(this,"已保存密码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delpwd:
                editor.putString("qqnumber", null);
                editor.putString("password", null);
                editor.apply();
                Toast.makeText(this,"清除成功，重启后生效", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
