package com.nuaa233.sqlite.Disscussion.MyPush;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nuaa233.sqlite.Disscussion.newItem.ItemModel;
import com.nuaa233.sqlite.R;
import com.nuaa233.sqlite.db.MyDatabaseHelper;

import java.util.List;

public class MyPushItemAdapter extends ArrayAdapter {
    private int resource;
    private List<MyPushItemModel> listItems;
    private Context context;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    public MyPushItemAdapter(Context context, int resource, List<MyPushItemModel> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.listItems = objects;
        this.context = context;
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public MyPushItemModel getItem(int position) { return listItems.get(position); }

    @Override
    public int getCount() {
        if (null == listItems) {
            return 0;
        }
        return listItems.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        MyPushItemModel item = listItems.get(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.tvDate = (TextView) view.findViewById(R.id.tvDate);
            viewHolder.tvContent = (TextView) view.findViewById(R.id.tvContent);
            viewHolder.deletepost = (LinearLayout) view.findViewById(R.id.delete_post);
            view.setTag(viewHolder);
        }
        else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName.setText(item.getUsrNick());
        viewHolder.tvDate.setText(item.getPostDate());
        viewHolder.tvContent.setText(item.getPostContent());

        viewHolder.deletepost.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final MyPushItemModel item = listItems.get(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Hello!");
                dialog.setMessage("是否删除该动态？");
                dialog.setPositiveButton("删掉它！", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper = new MyDatabaseHelper(context, "Info.db", null, 1);
                        db = dbHelper.getWritableDatabase();
                        db.execSQL("delete from Post where _id like ? and content like ?", new Object[] {item.getUsrId(), item.getPostContent()});
                    }
                });
                dialog.setNegativeButton("让我再想想...", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
                return true;
            }
        });

        return view;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvDate;
        TextView tvContent;
        LinearLayout deletepost;
    }
}
