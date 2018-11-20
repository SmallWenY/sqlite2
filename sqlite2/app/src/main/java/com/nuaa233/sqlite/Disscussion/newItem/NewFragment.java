package com.nuaa233.sqlite.Disscussion.newItem;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.TextView;
import android.widget.Toast;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class NewFragment extends Fragment {
    View view;
    private itemAdapter adapter;
    private List<ItemModel> itemList = null;
    private List<ItemModel> data = null;
    private ImageView iv_refresh;
    private ImageView back;
    private ListView listview;
    private TextView tv_userName;
    private String postName;// 发说说的名字
    private String currentUser;//用户id
    private static long lastClickTime;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        currentUser = getActivity().getIntent().getStringExtra("id");
        initData();
        Log.i("666", itemList.size() + "itemList大小");

        View DataLayout = inflater.inflate(R.layout.fragment_new, container, false);
        tv_userName = (TextView) DataLayout.findViewById(R.id.tv_userName);
        iv_refresh = (ImageView) DataLayout.findViewById(R.id.iv_refresh);
        listview = (ListView) DataLayout.findViewById(R.id.shuoList);
        back = (ImageView) DataLayout.findViewById(R.id.back);

        //userName = getActivity().getIntent().getStringExtra("id");
        //tv_userName.setText(postName);

        final Animation circle_anim = AnimationUtils.loadAnimation(getActivity(), R.anim.refresh_anim);
        LinearInterpolator interpolator = new LinearInterpolator(); // 设置匀速旋转，在xml文件中设置会出现卡顿
        circle_anim.setInterpolator(interpolator);

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

        return DataLayout;
    }


    private void initData() {
        itemList = new ArrayList<ItemModel>();
        String content, time, id;

        //String id = getActivity().getIntent().getStringExtra("id");       // Bundle bundle = getArguments(); fragment收数据用法

        dbHelper = new MyDatabaseHelper(getActivity(), "Info.db", null, 1);  //注意这里的context是getActivity()
        db = dbHelper.getWritableDatabase();
        cursor = db.query("Post", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                ItemModel item = new ItemModel();
                id = cursor.getString(cursor.getColumnIndex("_id"));
                content = cursor.getString(cursor.getColumnIndex("content"));
                postName = cursor.getString(cursor.getColumnIndex("nickname"));
                time = cursor.getString(cursor.getColumnIndex("time"));

                Log.d("666", "in newfragment id is " + id);
                Log.d("666", "nickname is " + postName);
                Log.d("666", "content is " + content);
                Log.d("666", "time is " + time);

                item.setShuoContent(content);
                item.setShuoDate(time);
                item.setUserName(postName);
                item.setShuoId(id);
                item.setUsrId(currentUser);
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        Message msg = new Message();
        msg.obj = itemList;
        handler.sendMessage(msg);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            data = (List<ItemModel>) msg.obj;
            Log.i("666", data.size() + "数据大小");
            adapter = new itemAdapter(getActivity(), R.layout.item, data);
            listview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        };
    };

    public static boolean isFastDoubleClick () {
        long t = System.currentTimeMillis();
        long timeD = t - lastClickTime;
        if (0 < timeD && timeD < 4000) { // 500毫秒内按钮无效，这样可以控制快速点击，自己调整频率
            return true;
        }
        lastClickTime = t;
        return false;
    }
}
