package com.nuaa233.sqlite.Disscussion.newItem;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInfoActivity extends AppCompatActivity {
    ImageView back;
    TextView nickname, sex, phone, email, Id, age, followTv;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String post_id, usr_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        dbHelper = new MyDatabaseHelper(this, "Info.db", null, 1);  //注意这里的context是getActivity()
        db = dbHelper.getWritableDatabase();


        Log.d("666", "In UserInfoActivity");

        initView();
        initData();
        onClick();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        nickname = (TextView) findViewById(R.id.nickname);
        sex = (TextView) findViewById(R.id.sex);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        Id = (TextView) findViewById(R.id.id);
        age = (TextView) findViewById(R.id.age);
        followTv = (TextView) findViewById(R.id.followTv);
        Log.d("666", "In UserInfoActivity end initView");

    }

    private void initData() {
        Log.d("666", "In UserInfoActivity initData");

        Intent i= getIntent();
        Bundle data = i.getExtras();
        post_id = data.getString("post_id");
        usr_id = data.getString("usr_id");

        Log.d("666", "In UserInfoActivity get the id is " + post_id);

        cursor = db.rawQuery("select * from Usr_Info where _id like ?", new String[] {post_id});
        if (cursor.moveToFirst()) {
            nickname.setText(cursor.getString(cursor.getColumnIndex("nickname")));
            sex.setText(cursor.getString(cursor.getColumnIndex("sex")));
            phone.setText(cursor.getString(cursor.getColumnIndex("phone")));
            email.setText(cursor.getString(cursor.getColumnIndex("email")));
            Id.setText(cursor.getString(cursor.getColumnIndex("_id")));
            age.setText(cursor.getString(cursor.getColumnIndex("age")));
        }

        cursor = db.rawQuery("select * from Usr_Relation where follow_id like ? and usr_id like ?", new String[] {post_id, usr_id});
        if (cursor.moveToFirst()) {
            followTv.setText("已关注");
        }
    }

    private void onClick() {
        followTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (post_id.equals(usr_id)) {
                    Toast.makeText(UserInfoActivity.this, "不能自己关注自己哦！", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (followTv.getText().toString().equals("+关注")) {
                        followTv.setText("已关注");
                        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                        String time = dateformat.format(curDate);
                        db.execSQL("insert into Usr_Relation(usr_id, follow_id, follow_nick, follow_date) values(?, ?, ?, ?)", new Object[]{usr_id, post_id, nickname.getText().toString(), time});
                        Toast.makeText(UserInfoActivity.this, "关注成功！", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        followTv.setText("+关注");
                        db.execSQL("delete from Usr_Relation where usr_id = ? and follow_id = ?", new Object[]{usr_id, post_id});
                        Toast.makeText(UserInfoActivity.this, "取消关注成功！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
