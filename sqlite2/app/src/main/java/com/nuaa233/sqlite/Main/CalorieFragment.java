package com.nuaa233.sqlite.Main;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CalorieFragment extends Fragment {
    View view;
    private static final float TEXT_SIZE = 18;
    private PieChart mPieChart;
    private List<MonthData> mMonthData;
    private String id, can1 = "一食堂", can2 = "二食堂", can3 = "三食堂";
    private int cal_can1, cal_can2, cal_can3;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor1, cursor2, cursor3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calorie, container, false);
        mPieChart = (PieChart) view.findViewById(R.id.chart_view);
        initData();
        initPieData();
        return view;
    }

    private void initData() {
        id = getActivity().getIntent().getStringExtra("id");       // Bundle bundle = getArguments(); fragment收数据用法

        dbHelper = new MyDatabaseHelper(getActivity(), "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        cursor1 = db.rawQuery("select * from UsrCanInfo where usr_id = ? and dish_can = ?", new String[] {id, can1});
        cursor2 = db.rawQuery("select * from UsrCanInfo where usr_id = ? and dish_can = ?", new String[] {id, can2});
        cursor3 = db.rawQuery("select * from UsrCanInfo where usr_id = ? and dish_can = ?", new String[] {id, can3});

        if (cursor1.moveToFirst()) {
            do {
                cal_can1 += cursor1.getInt(cursor1.getColumnIndex("dish_calorie"));
            } while (cursor1.moveToNext());
        }

        if (cursor2.moveToFirst()) {
            do {
                cal_can2 += cursor2.getInt(cursor2.getColumnIndex("dish_calorie"));
            } while (cursor2.moveToNext());
        }

        if (cursor3.moveToFirst()) {
            do {
                cal_can3 += cursor3.getInt(cursor3.getColumnIndex("dish_calorie"));
            } while (cursor3.moveToNext());
        }

        mMonthData = new ArrayList<>();
        MonthData can1 = new MonthData();
        can1.setLabel("一食堂");
        can1.setValue(cal_can1);
        MonthData can2 = new MonthData();
        can2.setLabel("二食堂");
        can2.setValue(cal_can2);
        MonthData can3 = new MonthData();
        can3.setLabel("三食堂");
        can3.setValue(cal_can3);
        mMonthData.add(can1);
        mMonthData.add(can2);
        mMonthData.add(can3);
    }

    private void  initPieData(){
        PieData mPieData = new PieData();
        List<PieEntry> mEntry = new ArrayList<>();
        String mLabel = "Others";
        for (MonthData monthData : mMonthData){
            PieEntry entry = new PieEntry(monthData.getValue(),monthData.getLabel());
            mEntry.add(entry);
        }
        PieDataSet mDataSet = new PieDataSet(mEntry,mLabel);
        mDataSet.setColors(Color.rgb(255,69,0),Color.rgb(255,185,15),Color.rgb(30,144,255));
        mPieData.setDataSet(mDataSet);
        mPieData.setValueTextSize(TEXT_SIZE);
        mPieData.setValueTextColor(Color.WHITE);
        mPieChart.setDescription(new Description());
        mPieChart.setData(mPieData);
    }
}
