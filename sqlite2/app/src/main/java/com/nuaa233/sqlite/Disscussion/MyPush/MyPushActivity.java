package com.nuaa233.sqlite.Disscussion.MyPush;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MyPushActivity extends AppCompatActivity {
    private ImageView back;
    private MyPushItemAdapter adapter;
    private List<MyPushItemModel> itemList = null;
    private List<MyPushItemModel> data = null;
    private String usrId, content, time, nickname;
    private ListView listView;
    private ImageView iv_refresh;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_push);

        initView();
        initData();
        onClick();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        listView = (ListView) findViewById(R.id.myPush);
    }

    private void initData() {
        Intent i= getIntent();
        Bundle data = i.getExtras();
        usrId = data.getString("id");

        Log.d("666", "in initData id is " + usrId);

        itemList = new ArrayList<MyPushItemModel>();
        dbHelper = new MyDatabaseHelper(MyPushActivity.this, "Info.db", null, 1);  //注意这里的context是getActivity()
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("select * from Post where _id like ?", new String[] {usrId});

        if (cursor.moveToFirst()) {
            do {
                MyPushItemModel item = new MyPushItemModel();
                content = cursor.getString(cursor.getColumnIndex("content"));
                nickname = cursor.getString(cursor.getColumnIndex("nickname"));
                time = cursor.getString(cursor.getColumnIndex("time"));

                Log.d("666", "in mypushactivity id is " + usrId);
                Log.d("666", "nickname is " + nickname);
                Log.d("666", "content is " + content);
                Log.d("666", "time is " + time);

                item.setPostContent(content);
                item.setPostDate(time);
                item.setUsrId(usrId);
                item.setUsrNick(nickname);
                itemList.add(item);
            }while (cursor.moveToNext());
        }
        Message msg = new Message();
        msg.obj = itemList;
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            data = (List<MyPushItemModel>) msg.obj;
            adapter = new MyPushItemAdapter(MyPushActivity.this, R.layout.item_my_push, data);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        };
    };

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                Toast.makeText(MyPushActivity.this, "刷新成功！", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
