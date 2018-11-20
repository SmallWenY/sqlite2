package com.nuaa233.sqlite.Disscussion.MyFollow;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MyFollowActivity extends AppCompatActivity {
    private ImageView back;
    private MyFollowItemAdapter adapter;
    private List<MyFollowItemModel> itemList = null;
    private List<MyFollowItemModel> data = null;
    private String usrId, followId, followNick, followDate;
    private ListView listView;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow);
        dbHelper = new MyDatabaseHelper(MyFollowActivity.this, "Info.db", null, 1);  //注意这里的context是getActivity()
        db = dbHelper.getWritableDatabase();

        initView();
        initData();
        onClick();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        listView = (ListView) findViewById(R.id.myFollow);
    }

    private void initData() {
        Intent i= getIntent();
        Bundle data = i.getExtras();
        usrId = data.getString("id");

        Log.d("666", "in initData id is " + usrId);

        itemList = new ArrayList<MyFollowItemModel>();
        cursor = db.rawQuery("select * from Usr_Relation where usr_id like ?", new String[] {usrId});

        if (cursor.moveToFirst()) {
            do {
                MyFollowItemModel item = new MyFollowItemModel();
                followId = cursor.getString(cursor.getColumnIndex("follow_id"));
                followNick = cursor.getString(cursor.getColumnIndex("follow_nick"));
                followDate = cursor.getString(cursor.getColumnIndex("follow_date"));

                Log.d("666", "in myfollowactivity usrId is " + usrId);
                Log.d("666", "nickname is " + followNick);
                Log.d("666", "date is " + followDate);
                Log.d("666", "id is " + followId);

                item.setFollowDate(followDate);
                item.setFollowId(followId);
                item.setFollowNick(followNick);
                item.setUsrId(usrId);
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        Message msg = new Message();
        msg.obj = itemList;
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            data = (List<MyFollowItemModel>) msg.obj;
            adapter = new MyFollowItemAdapter(MyFollowActivity.this, R.layout.item_my_follow, data);
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
    }
}
