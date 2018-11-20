package com.nuaa233.sqlite.Disscussion.MyFollow;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyFollowItemAdapter extends ArrayAdapter {
    private int resource;
    private List<MyFollowItemModel> listItems;
    private Context context;
    private String usrId, followId, followNick, followDate;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    public MyFollowItemAdapter(Context context, int resource, List<MyFollowItemModel> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.listItems = objects;
        this.context = context;
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public MyFollowItemModel getItem(int position) { return listItems.get(position); }

    @Override
    public int getCount() {
        if (null == listItems) {
            return 0;
        }
        return listItems.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        MyFollowItemModel item = listItems.get(position);
        final View view;
        final ViewHolder viewHolder;
        dbHelper = new MyDatabaseHelper(context, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.tvDate = (TextView) view.findViewById(R.id.tvDate);
            viewHolder.follow = (TextView) view.findViewById(R.id.follow);
            viewHolder.tvId = (TextView) view.findViewById(R.id.tvId);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName.setText(item.getFollowNick());
        viewHolder.tvDate.setText(item.getFollowDate());
        viewHolder.tvId.setText(item.getFollowId());
        usrId = item.getUsrId();
        followNick = item.getFollowNick();
        followDate = item.getFollowDate();
        followId = item.getFollowId();

        Log.d("666", "in followadapter usrId is " + usrId);
        Log.d("666", "nickname is " + followNick);
        Log.d("666", "date is " + followDate);
        Log.d("666", "id is " + followId);

        viewHolder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.follow.getText().toString().equals("已关注")) {
                    MyFollowItemModel item = listItems.get(position);
                    viewHolder.follow.setText("+关注");
                    db.execSQL("delete from Usr_Relation where usr_id = ? and follow_id = ?", new Object[] {item.getUsrId(), item.getFollowId()});
                    //这里一定要使用item获取信息，不要使用前面的变量
                    Toast.makeText(context, "取消关注成功！", Toast.LENGTH_SHORT).show();
                }
                else {
                    MyFollowItemModel item = listItems.get(position);
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String time = dateformat.format(curDate);
                    viewHolder.follow.setText("已关注");
                    db.execSQL("insert into Usr_Relation(usr_id, follow_id, follow_nick, follow_date) values(?, ?, ?, ?)", new Object[]{item.getUsrId(), item.getFollowId(), item.getFollowNick(), time});
                    Toast.makeText(context, "关注成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvDate;
        TextView tvId;
        TextView follow;
    }
}
