package com.nuaa233.sqlite.Main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuaa233.sqlite.Login.LoginActivity;
import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.Usr_Info.ChangeUsrIconActivity;
import com.nuaa233.sqlite.Usr_Info.ChangeUsrInfoActivity;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

public class UsrInfoFragment extends Fragment {
    View view;
    TextView nicknametextView, sextextView, phoneView, emailView, idView, ageView;
    RelativeLayout changedataBotton;
    Button loginout;
    ImageView icon;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_usr_info, container, false);
        initView();
        initData();
        onClick();
        return view;
    }

    private void initView() {
        nicknametextView = (TextView) view.findViewById(R.id.nickname);
        sextextView = (TextView) view.findViewById(R.id.sex);
        phoneView = (TextView) view.findViewById(R.id.phone);
        emailView = (TextView) view.findViewById(R.id.email);
        idView = (TextView) view.findViewById(R.id.id);
        changedataBotton = (RelativeLayout) view.findViewById(R.id.changedata);
        ageView = (TextView) view.findViewById(R.id.age);
        loginout = (Button) view.findViewById(R.id.btnLoginout);
        icon = (ImageView) view.findViewById(R.id.usricon);
    }

    private void initData() {
        String id = getActivity().getIntent().getStringExtra("id");       // Bundle bundle = getArguments(); fragment收数据用法

        dbHelper = new MyDatabaseHelper(getActivity(), "Info.db", null, 1);  //注意这里的context是getActivity()
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("select * from Usr_Info where _id like ?", new String[] {id});
        if (cursor.moveToFirst()) {
            nicknametextView.setText(cursor.getString(cursor.getColumnIndex("nickname")));
            sextextView.setText(cursor.getString(cursor.getColumnIndex("sex")));
            phoneView.setText(cursor.getString(cursor.getColumnIndex("phone")));
            emailView.setText(cursor.getString(cursor.getColumnIndex("email")));
            idView.setText(cursor.getString(cursor.getColumnIndex("_id")));
            ageView.setText(cursor.getString(cursor.getColumnIndex("age")));
        }
    }

    private void onClick() {
        changedataBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity().getApplicationContext(), ChangeUsrInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", idView.getText().toString());
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });

        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity().getApplicationContext(), LoginActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity().getApplicationContext(), ChangeUsrIconActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", idView.getText().toString());
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }
}
