package com.nuaa233.sqlite.Favorite;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nuaa233.sqlite.Disscussion.MyFollow.MyFollowActivity;
import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MyFavActivity extends AppCompatActivity {
    View view;
    private MyFavItemAdapter adapter;
    private List<MyFavItemModel> itemList = null;
    private List<MyFavItemModel> data = null;
    private ImageView iv_refresh;
    private ImageView back;
    private ListView listview;
    private String usrId, dishName, uploadTime, uploadUsrName, uploadUsrId, dishCalorie, canteen, dishprice;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fav);

        initView();
        initData();
        onClick();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        iv_refresh = (ImageView) findViewById(R.id.iv_refresh);
        listview = (ListView) findViewById(R.id.dishList);
    }

    private void initData() {
        Cursor cursor1;
        Intent i= getIntent();
        Bundle data = i.getExtras();
        usrId = data.getString("id");

        itemList = new ArrayList<MyFavItemModel>();

        Log.d("666", "test");

        dbHelper = new MyDatabaseHelper(MyFavActivity.this, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("select * from UsrCanInfo where usr_id = ?", new String[] {usrId});

        if (cursor.moveToFirst()) {
            do {
                MyFavItemModel item = new MyFavItemModel();

                dishName = cursor.getString(cursor.getColumnIndex("dish_name"));
                Log.d("666", "dishname is " + cursor.getString(cursor.getColumnIndex("dish_name")));

                cursor1 = db.rawQuery("select * from Can_One where dish_name = ?", new String[] {dishName});
                if (cursor1.getCount() == 0) {
                    cursor1 = db.rawQuery("select * from Can_Two where dish_name = ?", new String[] {dishName});
                    if (cursor1.getCount() == 0) {
                        cursor1 = db.rawQuery("select * from Can_Three where dish_name = ?", new String[] {dishName});
                    }
                }
                Log.d("666", "test1");

                cursor1.moveToFirst();

                uploadTime = cursor1.getString(cursor1.getColumnIndex("upload_time"));
                uploadUsrId = cursor1.getString(cursor1.getColumnIndex("upload_usr_id"));
                uploadUsrName = cursor1.getString(cursor1.getColumnIndex("upload_usr_name"));
                dishCalorie = cursor1.getString(cursor1.getColumnIndex("dish_calorie"));
                canteen = cursor.getString(cursor.getColumnIndex("dish_can"));
                dishprice = cursor1.getString(cursor1.getColumnIndex("dish_price"));

                Log.d("666", "test2");

                item.setDishName(dishName);
                item.setUploadTime(uploadTime);
                item.setUploadUsrId(uploadUsrId);
                item.setUploadUsrName(uploadUsrName);
                item.setDishcalorie(dishCalorie);
                item.setUsrId(usrId);
                item.setCanteen(canteen);
                item.setDishprice(dishprice);
                itemList.add(item);
            }while (cursor.moveToNext());
        }
        Log.d("666", "end item");
        Message msg = new Message();
        msg.obj = itemList;
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            data = (List<MyFavItemModel>) msg.obj;
            adapter = new MyFavItemAdapter(MyFavActivity.this, R.layout.item_my_fav, data);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
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
                Toast.makeText(MyFavActivity.this, "刷新成功！", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
