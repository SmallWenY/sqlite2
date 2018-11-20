package com.nuaa233.sqlite.Login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

public class RegisterActivity extends AppCompatActivity {
    EditText editText_usr_id, editText_usr_pwd, editText_usr_phone, editText_usr_email, editText_usr_nickname, editText_usr_sex, editText_usr_age;
    Button confirmButton;
    TextView link;
    CheckBox check;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editText_usr_id = (EditText) findViewById(R.id.usr_id);
        editText_usr_pwd = (EditText) findViewById(R.id.usr_pwd);
        editText_usr_phone = (EditText) findViewById(R.id.usr_phone);
        editText_usr_email = (EditText) findViewById(R.id.usr_email);
        editText_usr_nickname = (EditText) findViewById(R.id.usr_nickname);
        editText_usr_sex = (EditText) findViewById(R.id.usr_sex);
        editText_usr_age = (EditText) findViewById(R.id.usr_age);
        check = (CheckBox) findViewById(R.id.check);
        link = (TextView) findViewById(R.id.link);
        confirmButton = (Button) findViewById(R.id.registerButton);

        dbHelper = new MyDatabaseHelper(this, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断id是否重复
                cursor = db.rawQuery("select * from Usr_Info where _id like ? ", new String[] {editText_usr_id.getText().toString()});
                int count = 0;
                count = cursor.getCount();
                if (count == 0) {
                    if (check.isChecked()) {
                        db.execSQL("insert into Usr_Info(_id, pwd, phone, email, nickname, sex, age) values(?, ?, ?, ?, ?, ?, ?)", new Object[]{editText_usr_id.getText().toString(),
                                editText_usr_pwd.getText().toString(), editText_usr_phone.getText().toString(), editText_usr_email.getText().toString(), editText_usr_nickname.getText().toString(),
                                editText_usr_sex.getText().toString(), editText_usr_age.getText().toString()});
                        cursor = db.rawQuery("select * from Usr_Info", null);
                        Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                    else {
                        Toast.makeText(RegisterActivity.this, "请先阅读用户服务协议！", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this, "此学号已经注册过啦！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LinkActivity.class);
                startActivity(intent);
            }
        });

    }
}