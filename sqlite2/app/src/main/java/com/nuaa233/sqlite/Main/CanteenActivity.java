package com.nuaa233.sqlite.Main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuaa233.sqlite.Canteen.Canteen1.Canteen1Fragment;
import com.nuaa233.sqlite.Canteen.Canteen2.Canteen2Fragment;
import com.nuaa233.sqlite.Canteen.Canteen3.Canteen3Fragment;
import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import org.w3c.dom.Text;

public class CanteenActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String id = new String("test");
    private RelativeLayout canteen1, canteen2, canteen3;
    private TextView tx_can1, tx_can2, tx_can3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canteen);

        addFragmentToStack(new Canteen1Fragment());

        initView();
        initData();
        onClick();
    }

    private void initView() {
        canteen1 = findViewById(R.id.canteen1);
        canteen2 = findViewById(R.id.canteen2);
        canteen3 = findViewById(R.id.canteen3);
        tx_can1 = findViewById(R.id.tx_canteen1);
        tx_can2 = findViewById(R.id.tx_canteen2);
        tx_can3 = findViewById(R.id.tx_canteen3);
    }

    private void initData() {
        Intent i = getIntent();
        Bundle data = i.getExtras();
        id = data.getString("id");

        dbHelper = new MyDatabaseHelper(this, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery("select * from Usr_Info where _id like ?", new String[] {id});
    }

    private void onClick() {
        canteen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelection();
                tx_can1.setTextColor(Color.parseColor("#1296db"));
                Canteen1Fragment canteen1Fragment = new Canteen1Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                canteen1Fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.canteen_content, canteen1Fragment).commit();
            }
        });

        canteen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelection();
                tx_can2.setTextColor(Color.parseColor("#1296db"));
                Canteen2Fragment canteen2Fragment = new Canteen2Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                canteen2Fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.canteen_content, canteen2Fragment).commit();

            }
        });

        canteen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelection();
                tx_can3.setTextColor(Color.parseColor("#1296db"));
                Canteen3Fragment canteen3Fragment = new Canteen3Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                canteen3Fragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.canteen_content, canteen3Fragment).commit();

            }
        });

    }

    private void addFragmentToStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.canteen_content, fragment).commit();
    }

    private void clearSelection() {
        tx_can1.setTextColor(Color.parseColor("#82858b"));
        tx_can2.setTextColor(Color.parseColor("#82858b"));
        tx_can3.setTextColor(Color.parseColor("#82858b"));
    }

}
