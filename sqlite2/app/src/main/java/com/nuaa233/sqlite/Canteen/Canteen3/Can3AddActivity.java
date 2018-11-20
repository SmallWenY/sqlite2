package com.nuaa233.sqlite.Canteen.Canteen3;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Can3AddActivity extends AppCompatActivity {
    EditText dishname, dishcalorie, dishprice;
    TextView back, confirm;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String id = new String("test");

    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
    String time = dateformat.format(curDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_can3_add);

        initView();
        initData();
        onClick();
    }

    private void initView() {
        dishname = (EditText) findViewById(R.id.add_dishname_edit);
        dishcalorie = (EditText) findViewById(R.id.add_dishcalorie_edit);
        dishprice = (EditText) findViewById(R.id.add_dishprice_edit);
        back = (TextView) findViewById(R.id.back);
        confirm = (TextView) findViewById(R.id.confirm);
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        dbHelper = new MyDatabaseHelper(this, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    private void onClick() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                cursor = db.rawQuery("select * from Can_Three where dish_name = ?", new String[] {dishname.getText().toString()});
                count = cursor.getCount();
                if (count == 0) {
                    String nickname;
                    cursor = db.rawQuery("select * from Usr_Info where _id like ?", new String[] {id});
                    if (cursor.moveToFirst()) {
                        curDate = new Date(System.currentTimeMillis());
                        time = dateformat.format(curDate);
                        nickname = cursor.getString(cursor.getColumnIndex("nickname"));
                        db.execSQL("insert into Can_Three(upload_time, upload_usr_id, upload_usr_name, dish_calorie, dish_price, dish_name) values(?, ?, ?, ?, ?, ?)", new Object[] {time, id, nickname, dishcalorie.getText().toString(), dishprice.getText().toString(), dishname.getText().toString()});
                        Toast.makeText(Can3AddActivity.this, "菜品添加成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
                else {
                    Toast.makeText(Can3AddActivity.this, "菜品已经被添加过啦！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
