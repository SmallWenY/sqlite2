package com.nuaa233.sqlite.Login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.nuaa233.sqlite.db.MyDatabaseHelper;
import com.nuaa233.sqlite.R;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    Button updateData, addData, deleteButton, queryButton, clearButton, returnButton;
    EditText editText_id, editText2_pwd;
    ListView listView;
    SQLiteDatabase db;
    Cursor cursor;
    SimpleCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateData = (Button)findViewById(R.id.update_data);
        addData = (Button)findViewById(R.id.add_data);
        deleteButton = (Button)findViewById(R.id.delete_data);
        queryButton = (Button)findViewById(R.id.query_data);
        clearButton = (Button)findViewById(R.id.clear_data);
        returnButton = (Button)findViewById(R.id.returnButton) ;
        listView = (ListView)findViewById(R.id.listView);
        editText_id = (EditText)findViewById(R.id.editText_id);
        editText2_pwd = (EditText)findViewById(R.id.editText2_pwd);

        dbHelper = new MyDatabaseHelper(this, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        //cursor = db.query("Info", null, null, null, null, null, null);
        cursor = db.rawQuery("select * from Usr_Info", null);
        //ok
        cursorAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, new String[]{"_id", "pwd"}, new int[]{android.R.id.text1, android.R.id.text2}, cursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(cursorAdapter);

        onClick();

        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Id = editText_id.getText().toString();
                String Pwd = editText2_pwd.getText().toString();

                cursor = db.rawQuery("select * from Admin_Info where _id = ?", new String[]{Id});

                int count = cursor.getCount();

                if (count == 0) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("Oh!");
                    dialog.setMessage("看起来这个ID并没有注册过！");
                    dialog.setPositiveButton("好~", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                    return;
                }

                if (cursor.moveToFirst()) {
                    do {
                        String pwd = cursor.getString(cursor.getColumnIndex("pwd"));

                        if (Pwd.equals(pwd) && count != 0) {
                            Intent intent = new Intent(MainActivity.this, StartActivity.class);
                            startActivity(intent);
                        }
                        else if (!Pwd.equals(pwd) && count != 0) {
                            count--;
                        }

                        if(count == 0) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                            dialog.setTitle("Oh!");
                            dialog.setMessage("输入的学号或密码错误！");
                            dialog.setPositiveButton("好~", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            dialog.show();
                            break;
                        }
                    }while(cursor.moveToNext());
                }
            }
        });*/

        cursor.close();
    }

    private void onClick() {
        addData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                add();
            }
        });

        updateData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                update();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                delete();
            }
        });

        queryButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                query();
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.removeAllViewsInLayout();  //清空显示数据
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void add() {
        cursor = db.rawQuery("select * from Usr_Info where _id like ? ", new String[] {editText_id.getText().toString()});

        //判断id是否重复
        if (cursor.getCount() == 0) {
            db.execSQL("insert into Usr_Info(_id, pwd) values(?, ?)", new Object[]{editText_id.getText().toString(), editText2_pwd.getText().toString()});
            cursor = db.rawQuery("select * from Usr_Info", null);
            cursorAdapter.swapCursor(cursor);
        }
        else {
            Toast.makeText(this, "This id has already signed up!", Toast.LENGTH_SHORT).show();
            cursor = db.rawQuery("select * from Usr_Info", null);
            cursorAdapter.swapCursor(cursor);
        }
    }

    public void delete() {
        db.execSQL("delete from Usr_Info where _id = ?", new Object[]{editText_id.getText().toString()});
        cursor = db.rawQuery("select * from Usr_Info", null);
        cursorAdapter.swapCursor(cursor);
    }

    public void update() {
        db.execSQL("update Usr_Info set pwd = ? where _id = ?", new Object[]{editText2_pwd.getText().toString(), editText_id.getText().toString()});
        cursor = db.rawQuery("select * from Usr_Info", null);
        cursorAdapter.swapCursor(cursor);
    }

    public void query() {
        //cursor = db.query("Info", null, "_id like ?", new String[]{"%" + editText_id.getText().toString() + "%"}, null, null, null);
        cursor = db.rawQuery("select * from Usr_Info where _id like ?", new String[]{"%"+editText_id.getText().toString()+"%"});
        Log.d("hallo", "admin query count" + cursor.getCount());
        cursorAdapter.swapCursor(cursor);
    }
}
