package com.nuaa233.sqlite.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.CpuUsageInfo;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.widget.Toast;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String CREATE_ADMININFO = "create table Admin_Info ("
            + "id integer primary key autoincrement, "
            + "_id integer, "
            + "pwd text, "
            + "email text, "
            + "nickname text, "
            + "age integer)";

    public static final String CREATE_USRINFO = "create table Usr_Info ("
            + "id integer primary key autoincrement, "
            + "_id integer, "
            + "pwd text, "
            + "phone integer, "
            + "email text, "
            + "nickname text, "
            + "sex integer, "
            + "calorie integer default 0, "
            + "expense integer default 0, "
            + "age text)";

    public static final String CREATE_POST = "create table Post ("
            + "id integer primary key autoincrement, "
            + "_id integer, "
            + "nickname text, "
            + "time text, "
            + "content text)";

    public static final String CREATE_USRRELATION = "create table Usr_Relation ("
            + "id integer primary key autoincrement, "
            + "usr_id integer, "
            + "follow_id integer, "
            + "follow_nick text, "
            + "follow_date text)";

    public static final String CREATE_CANTEEN1 = "create table Can_One ("
            + "id integer primary key autoincrement, "
            + "upload_time text, "
            + "upload_usr_id integer, "
            + "upload_usr_name text, "
            + "dish_calorie integer, "
            + "dish_price integer, "
            + "dish_name text)";

    public static final String CREATE_CANTEEN2 = "create table Can_Two ("
            + "id integer primary key autoincrement, "
            + "upload_time text, "
            + "upload_usr_id integer, "
            + "upload_usr_name text, "
            + "dish_calorie integer, "
            + "dish_price integer, "
            + "dish_name text)";

    public static final String CREATE_CANTEEN3 = "create table Can_Three ("
            + "id integer primary key autoincrement, "
            + "upload_time text, "
            + "upload_usr_id integer, "
            + "upload_usr_name text, "
            + "dish_calorie integer, "
            + "dish_price integer, "
            + "dish_name text)";

    public static final String CREATE_USRCANINFO = "create table UsrCanInfo ("
            + "id integer primary key autoincrement, "
            + "dish_name text, "
            + "dish_can text, "
            + "dish_calorie integer, "
            + "dish_price integer, "
            + "usr_id integer)";

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ADMININFO);
        db.execSQL(CREATE_USRINFO);
        db.execSQL(CREATE_POST);
        db.execSQL(CREATE_USRRELATION);
        db.execSQL(CREATE_CANTEEN1);
        db.execSQL(CREATE_CANTEEN2);
        db.execSQL(CREATE_CANTEEN3);
        db.execSQL(CREATE_USRCANINFO);
        Toast.makeText(mContext, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Admin_Info");
        db.execSQL("drop table if exists Usr_Info");
        db.execSQL("drop table if exists Post");
        db.execSQL("drop table if exists Usr_Relation");
        db.execSQL("drop table if exists Can_One");
        db.execSQL("drop table if exists Can_Two");
        db.execSQL("drop table if exists Can_Three");
        db.execSQL("drop table if exists UsrCanInfo");
        onCreate(db);
    }
}
