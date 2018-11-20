package com.nuaa233.sqlite.Usr_Info;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

public class ChangeUsrInfoActivity extends AppCompatActivity {
    EditText changenickname, changepassword, changephone, changeemail, changesex, changeage;
    TextView back, confirm;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String id = new String("test");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_usr_info);
        initView();
        initData();
        onClick();
    }

    private void initView() {
        changenickname = (EditText) findViewById(R.id.change_nickname_edit);
        changepassword = (EditText) findViewById(R.id.change_password_edit);
        changephone = (EditText) findViewById(R.id.change_phone_edit);
        changeemail = (EditText) findViewById(R.id.change_mail_edit);
        changesex = (EditText) findViewById(R.id.change_sex_edit);
        changeage = (EditText) findViewById(R.id.change_age_edit);
        back = (TextView) findViewById(R.id.back);
        confirm = (TextView) findViewById(R.id.confirm);
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");

        dbHelper = new MyDatabaseHelper(this, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("select * from Usr_Info where _id like ?", new String[] {id});
        if (cursor.moveToFirst()) {
            changenickname.setHint(cursor.getString(cursor.getColumnIndex("nickname")));
            //changepassword.setHint(cursor.getString(cursor.getColumnIndex("pwd")));
            changephone.setHint(cursor.getString(cursor.getColumnIndex("phone")));
            changeemail.setHint(cursor.getString(cursor.getColumnIndex("email")));
            changesex.setHint(cursor.getString(cursor.getColumnIndex("sex")));
            changeage.setHint(cursor.getString(cursor.getColumnIndex("age")));
        }
    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changenickname.getText().toString().length() != 0) {
                    db.execSQL("update Usr_Info set nickname = ? where _id = ?", new Object[]{changenickname.getText().toString(), id});
                    Toast.makeText(ChangeUsrInfoActivity.this, "昵称修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (changepassword.getText().toString().length() != 0) {
                    db.execSQL("update Usr_Info set pwd = ? where _id = ?", new Object[]{changepassword.getText().toString(), id});
                    Toast.makeText(ChangeUsrInfoActivity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (changephone.getText().toString().length() != 0) {
                    db.execSQL("update Usr_Info set phone = ? where _id = ?", new Object[]{changephone.getText().toString(), id});
                    Toast.makeText(ChangeUsrInfoActivity.this, "手机号修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (changeemail.getText().toString().length() != 0) {
                    db.execSQL("update Usr_Info set email = ? where _id = ?", new Object[]{changeemail.getText().toString(), id});
                    Toast.makeText(ChangeUsrInfoActivity.this, "邮箱修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (changesex.getText().toString().length() != 0) {
                    db.execSQL("update Usr_Info set sex = ? where _id = ?", new Object[]{changesex.getText().toString(), id});
                    Toast.makeText(ChangeUsrInfoActivity.this, "性别修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                if (changeage.getText().toString().length() != 0) {
                    db.execSQL("update Usr_Info set age = ? where _id = ?", new Object[]{changeage.getText().toString(), id});
                    Toast.makeText(ChangeUsrInfoActivity.this, "年龄修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
