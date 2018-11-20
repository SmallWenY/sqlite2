package com.nuaa233.sqlite.Disscussion.Mine;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import org.w3c.dom.Text;

public class MyInfoActivity extends AppCompatActivity {
    //Activity不要用view
    ImageView back;
    TextView nicknametextView, sextextView, phoneView, emailView, idView, ageView, followTv;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        initView();
        initData();
        onClick();
    }

    private void initView() {
        back = (ImageView) findViewById(R.id.back);
        nicknametextView = (TextView) findViewById(R.id.nickname);
        sextextView = (TextView) findViewById(R.id.sex);
        phoneView = (TextView) findViewById(R.id.phone);
        emailView = (TextView) findViewById(R.id.email);
        idView = (TextView) findViewById(R.id.id);
        ageView = (TextView) findViewById(R.id.age);
        followTv = (TextView) findViewById(R.id.followTv);
    }

    private void initData() {
        Intent i= getIntent();
        Bundle data = i.getExtras();
        id = data.getString("id");

        dbHelper = new MyDatabaseHelper(this, "Info.db", null, 1);  //注意这里的context是getActivity()
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
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
