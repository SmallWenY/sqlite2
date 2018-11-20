package com.nuaa233.sqlite.Disscussion.Mine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuaa233.sqlite.Disscussion.Mine.AboutUsActivity;
import com.nuaa233.sqlite.Disscussion.Mine.MyInfoActivity;
import com.nuaa233.sqlite.Disscussion.Mine.MyTucaoActivity;
import com.nuaa233.sqlite.Disscussion.MyFollow.MyFollowActivity;
import com.nuaa233.sqlite.Disscussion.MyPush.MyPushActivity;
import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

public class MineFragment extends Fragment {
    View view;
    private ImageView back;
    private RelativeLayout info, myPush, myFollow, myTucao, aboutUs;
    private TextView nickname;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursorUserInfo;
    String id;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);

        initView();
        initData();
        onClick();
        return view;
    }

    private void initView() {
        back = (ImageView) view.findViewById(R.id.back);
        info = (RelativeLayout) view.findViewById(R.id.infoRelativeLayout);
        myPush = (RelativeLayout) view.findViewById(R.id.myPush);
        myFollow = (RelativeLayout) view.findViewById(R.id.myFollow);
        myTucao = (RelativeLayout) view.findViewById(R.id.myTucao);
        nickname = (TextView) view.findViewById(R.id.tv_name);
        aboutUs = (RelativeLayout) view.findViewById(R.id.aboutUs);
        }

    private void initData() {
        id = getActivity().getIntent().getStringExtra("id");       // Bundle bundle = getArguments(); fragment收数据用法
        dbHelper = new MyDatabaseHelper(getActivity(), "Info.db", null, 1);  //注意这里的context是getActivity()
        db = dbHelper.getWritableDatabase();
        cursorUserInfo = db.rawQuery("select * from Usr_Info where _id like ?", new String[] {id});
        if (cursorUserInfo.moveToFirst()) {
            nickname.setText(cursorUserInfo.getString(cursorUserInfo.getColumnIndex("nickname")));
        }
    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity().getApplicationContext(), MyInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        myPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity().getApplicationContext(), MyPushActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        myFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity().getApplicationContext(), MyFollowActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        myTucao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity().getApplicationContext(), MyTucaoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity().getApplicationContext(), AboutUsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }
}
