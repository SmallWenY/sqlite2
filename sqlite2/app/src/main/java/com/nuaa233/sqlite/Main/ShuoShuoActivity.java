package com.nuaa233.sqlite.Main;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nuaa233.sqlite.Disscussion.AddActivity;
import com.nuaa233.sqlite.Disscussion.Mine.MineFragment;
import com.nuaa233.sqlite.Disscussion.newItem.NewFragment;
import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

public class ShuoShuoActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;
    String id = new String("test");
    private ImageView newsImage, settingImage;
    private TextView newsText, settingText;
    private RelativeLayout news, add, setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuo_shuo);

        Log.d("666", "id is " + id);

        addFragmentToStack(new NewFragment());

        initView();
        initData();
        onClick();
    }

    private void initView() {
        news = findViewById(R.id.news_layout);
        add = findViewById(R.id.add_layout);
        setting = findViewById(R.id.setting_layout);
        newsImage = (ImageView) findViewById(R.id.news_image);
        settingImage = (ImageView) findViewById(R.id.setting_image);
        newsText = (TextView) findViewById(R.id.news_text);
        settingText = (TextView) findViewById(R.id.setting_text);
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
//        switch (v.getId()) {
//            case R.id.news_layout:
//                Log.i("TAG", "模拟点击了");
//                setTabSelection(0);
//                break;
//            case R.id.add_layout:
//                Intent intent = new Intent();
//                intent.setClass(ShuoShuoActivity.this, AddActivity.class);
//                intent.putExtra("id", id);
//                startActivityForResult(intent, 1);
//                // MainActivity.this.findViewById(R.id.tv_refresh).performClick();
//                break;
//            case R.id.setting_layout:
//                setTabSelection(1);
//                break;
//            default:
//                break;
//        }

        news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSelection();
                newsImage.setImageResource(R.mipmap.news_selected);
                newsText.setTextColor(Color.parseColor("#1296db"));
                NewFragment mFragment = new NewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                mFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.contentView, mFragment).commit();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShuoShuoActivity.this, AddActivity.class);
                Log.d("666", "enter add id is " + id);
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearSelection();
                settingImage.setImageResource(R.mipmap.setting_selected);
                settingText.setTextColor(Color.parseColor("#1296db"));
                MineFragment mFragment = new MineFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", id);
                mFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.contentView, mFragment).commit();
            }
        });
    }

    private void addFragmentToStack(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.contentView, fragment).commit();
    }

    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {
        newsImage.setImageResource(R.mipmap.news_unselected);
        newsText.setTextColor(Color.parseColor("#82858b"));
        settingImage.setImageResource(R.mipmap.setting_unselected);
        settingText.setTextColor(Color.parseColor("#82858b"));
    }

//
//    /**
//     * 将所有的Fragment都置为隐藏状态。
//     *
//     * @param transaction
//     *            用于对Fragment执行操作的事务
//     */
//    private void hideFragments(FragmentTransaction transaction) {
//        if (newFragment != null) {
//            transaction.hide(newFragment);
//        }
//        if (mineFragment != null) {
//            transaction.hide(mineFragment);
//        }
//    }
}
