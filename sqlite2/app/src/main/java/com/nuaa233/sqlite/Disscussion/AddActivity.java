package com.nuaa233.sqlite.Disscussion;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddActivity extends AppCompatActivity {
    ImageView backView, publishView;
    EditText content;
    String id, nickname;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    String time = dateformat.format(curDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Log.d("666", "time is " + time);

        initView();
        initData();

        Log.d("666", "first id is " + id);
        Log.d("666", "first nickname is " + nickname);

        onClick();
    }

    private void initView() {
        backView = (ImageView) findViewById(R.id.back);
        publishView = (ImageView) findViewById(R.id.publish);
        content = (EditText) findViewById(R.id.editcontent);
    }

    private void initData() {
        dbHelper = new MyDatabaseHelper(this, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        Intent i= getIntent();
        Bundle bundle = i.getExtras();
        id = bundle.getString("id");
        cursor = db.rawQuery("select * from Usr_Info where _id like ?", new String[] {id});
        if (cursor.moveToFirst()) {
            nickname = cursor.getString(cursor.getColumnIndex("nickname"));
        }
    }

    private void onClick() {
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        publishView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (content.getText().toString().length() != 0) {
                    curDate = new Date(System.currentTimeMillis());
                    time = dateformat.format(curDate);

                    Log.d("666", "second id is " + id);
                    Log.d("666", "second nickname is " + nickname);
                    Log.d("666", "second time is " + time);
                    Log.d("666", "second content is " + content.getText().toString());

                    db.execSQL("insert into Post(_id, nickname, content, time) values(?, ?, ?, ?)", new Object[]{id, nickname, content.getText().toString(), time});
                    Toast.makeText(AddActivity.this, "发布成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
