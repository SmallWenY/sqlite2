package com.nuaa233.sqlite.Canteen.Canteen2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class Canteen2Fragment extends Fragment {
    View view;
    private Canteen2ItemAdapter adapter;
    private List<Canteen2Model> itemList = null;
    private List<Canteen2Model> data = null;
    private ImageView iv_refresh;
    private ImageView back;
    private ListView listview;
    private FloatingActionButton add;
    private String usrId, dishName, uploadTime, uploadUsrName, uploadUsrId, dishCalorie, dishPrice;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("666", "test");

        usrId = getActivity().getIntent().getStringExtra("id");
        initData();

        View DataLayout = inflater.inflate(R.layout.fragment_canteen2, container, false);
        iv_refresh = (ImageView) DataLayout.findViewById(R.id.iv_refresh);
        listview = (ListView) DataLayout.findViewById(R.id.dishList);
        back = (ImageView) DataLayout.findViewById(R.id.back);
        add = (FloatingActionButton) DataLayout.findViewById(R.id.fab_can2);

        iv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
                Toast.makeText(getActivity(), "刷新成功！", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), Can2AddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", usrId);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        return DataLayout;
    }

    private void initData() {
        itemList = new ArrayList<Canteen2Model>();

        dbHelper = new MyDatabaseHelper(getActivity(), "Info.db", null, 1);  //注意这里的context是getActivity()
        db = dbHelper.getWritableDatabase();
        cursor = db.query("Can_Two", null, null, null, null, null, null);

        Log.d("666", "test2");

        if (cursor.moveToFirst()) {
            do {
                Log.d("666", "test3");

                Canteen2Model item = new Canteen2Model();
                dishName = cursor.getString(cursor.getColumnIndex("dish_name"));
                uploadTime = cursor.getString(cursor.getColumnIndex("upload_time"));
                uploadUsrId = cursor.getString(cursor.getColumnIndex("upload_usr_id"));
                uploadUsrName = cursor.getString(cursor.getColumnIndex("upload_usr_name"));
                dishCalorie = cursor.getString(cursor.getColumnIndex("dish_calorie"));
                dishPrice = cursor.getString(cursor.getColumnIndex("dish_price"));

                Log.d("666", "test4");

                Log.d("666", "dishname is " + dishName);
                Log.d("666", "uploadId is " + uploadUsrId);


                item.setDishName(dishName);
                item.setUploadTime(uploadTime);
                item.setUploadUsrId(uploadUsrId);
                item.setUploadUsrName(uploadUsrName);
                item.setDishcalorie(dishCalorie);
                item.setDishprice(dishPrice);
                item.setUsrId(usrId);
                itemList.add(item);
            }while (cursor.moveToNext());
        }
        Message msg = new Message();
        msg.obj = itemList;
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            data = (List<Canteen2Model>) msg.obj;
            adapter = new Canteen2ItemAdapter(getActivity(), R.layout.item_can1, data);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    };
}
