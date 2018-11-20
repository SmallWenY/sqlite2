package com.nuaa233.sqlite.Favorite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.util.List;

public class MyFavItemAdapter extends ArrayAdapter {
    private int resource;
    private List<MyFavItemModel> listItems;
    private Context context;
    private String dishName, usrId;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;
    Cursor cursor;

    public MyFavItemAdapter(Context context, int resource, List<MyFavItemModel> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.listItems = objects;
        this.context = context;
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public MyFavItemModel getItem(int position) {return listItems.get(position);}

    @Override
    public int getCount() {
        if (null == listItems) {
            return 0;
        }
        return listItems.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        MyFavItemModel item = listItems.get(position);
        final View view;
        final ViewHolder viewHolder;
        dbHelper = new MyDatabaseHelper(context, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.tvDate = (TextView) view.findViewById(R.id.tvDate);
            viewHolder.tvUsrName = (TextView) view.findViewById(R.id.tvUsrName);
            viewHolder.love = (TextView) view.findViewById(R.id.love);
            viewHolder.dishcalorie = (TextView)  view.findViewById(R.id.dishcolorie);
            viewHolder.canteen = (TextView) view.findViewById(R.id.canteen);
            viewHolder.dishprice = (TextView) view.findViewById(R.id.dishprice);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName.setText(item.getDishName());
        viewHolder.tvUsrName.setText(item.getUploadUsrName());
        viewHolder.tvDate.setText(item.getUploadTime());
        viewHolder.dishcalorie.setText(item.getDishcalorie());
        viewHolder.canteen.setText(item.getCanteen());
        viewHolder.dishprice.setText(item.getDishprice());
        usrId = item.getUsrId();
        dishName = item.getDishName();

        Log.d("666", "dishname in MyFav adapter is " + dishName);

        cursor = db.rawQuery("select * from UsrCanInfo where dish_name = ? and usr_id = ?", new String[] {item.getDishName(), item.getUsrId()});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            Log.d("666", "进入更改添加状态 " + cursor.getString(cursor.getColumnIndex("dish_name")));
            viewHolder.love.setText("已添加");
        }

        viewHolder.love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.love.getText().toString().equals("已添加")) {
                    MyFavItemModel item = listItems.get(position);
                    viewHolder.love.setText("+添加");
                    db.execSQL("delete from UsrCanInfo where dish_name = ? and dish_can = ? and dish_calorie = ? and dish_price = ? and usr_id = ?", new Object[] {item.getDishName(), item.getCanteen(), item.getDishcalorie(), item.getDishprice(), item.getUsrId()});
                    db.execSQL("update Usr_Info set calorie = calorie - ? where _id = ?", new Object[] {item.getDishcalorie(), usrId});
                    db.execSQL("update Usr_Info set expense = expense - ? where _id = ?", new Object[] {item.getDishprice(), usrId});
                    Toast.makeText(context, "取消添加成功！", Toast.LENGTH_SHORT).show();
                }
                else {
                    MyFavItemModel item = listItems.get(position);
                    viewHolder.love.setText("已添加");
                    db.execSQL("insert into UsrCanInfo(dish_name, dish_can, dish_calorie, dish_price, usr_id) values(?, ?, ?, ?, ?)", new Object[] {item.getDishName(), item.getCanteen(), item.getDishcalorie(), item.getDishprice(), item.getUsrId()});
                    db.execSQL("update Usr_Info set calorie = calorie + ? where _id = ?", new Object[] {item.getDishcalorie(), usrId});
                    db.execSQL("update Usr_Info set expense = expense + ? where _id = ?", new Object[] {item.getDishprice(), usrId});
                    Toast.makeText(context, "添加成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvUsrName;
        TextView tvDate;
        TextView love;
        TextView dishcalorie;
        TextView canteen;
        TextView dishprice;
    }
}
