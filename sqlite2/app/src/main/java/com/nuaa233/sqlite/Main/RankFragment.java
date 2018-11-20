package com.nuaa233.sqlite.Main;

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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.Rank.RankItemAdapter;
import com.nuaa233.sqlite.Rank.RankItemModel;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class RankFragment extends Fragment {
    View view;
    private RankItemAdapter adapter;
    private List<RankItemModel> itemList = null;
    private List<RankItemModel> data = null;
    private ImageView back;
    private ListView listview;
    private String usrId, id, usrName, usrCalorie;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("666", "test");

        usrId = getActivity().getIntent().getStringExtra("id");
        initData();

        View DataLayout = inflater.inflate(R.layout.fragment_rank, container, false);
        listview = (ListView) DataLayout.findViewById(R.id.rank);
        back = (ImageView) DataLayout.findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return DataLayout;
    }

    private void initData() {
        itemList = new ArrayList<RankItemModel>();

        dbHelper = new MyDatabaseHelper(getActivity(), "Info.db", null, 1);  //注意这里的context是getActivity()
        db = dbHelper.getWritableDatabase();
        //cursor = db.query("Usr_Info", null, null, null, null, null, null);
        cursor = db.rawQuery("select * from Usr_Info order by calorie desc, _id asc", null);

        Log.d("666", "test2");

        if (cursor.moveToFirst()) {
            do {
                Log.d("666", "test3");

                RankItemModel item = new RankItemModel();
                id = cursor.getString(cursor.getColumnIndex("_id"));
                usrName = cursor.getString(cursor.getColumnIndex("nickname"));
                usrCalorie = cursor.getString(cursor.getColumnIndex("calorie"));

                Log.d("666", "id is " + id);
                Log.d("666", "calorie is " + usrCalorie);

                if (usrId.equals(id)) {
                    item.setName("我");
                }
                else {
                    item.setName(usrName);
                }

                item.setId(id);
                item.setCalorie(usrCalorie);
                itemList.add(item);
            }while (cursor.moveToNext());
        }
        Message msg = new Message();
        msg.obj = itemList;
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            data = (List<RankItemModel>) msg.obj;
            adapter = new RankItemAdapter(getActivity(), R.layout.item_rank, data);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    };
}
