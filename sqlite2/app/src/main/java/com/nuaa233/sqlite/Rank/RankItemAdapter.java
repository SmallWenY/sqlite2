package com.nuaa233.sqlite.Rank;

import android.content.Context;
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

public class RankItemAdapter extends ArrayAdapter {
    private int resource;
    private List<RankItemModel> listItems;
    private Context context;
    private MyDatabaseHelper dbHelper;
    SQLiteDatabase db;

    public RankItemAdapter(Context context, int resource, List<RankItemModel> objects) {
        super(context, resource, objects);
        this.resource = resource;
        this.listItems = objects;
        this.context = context;
    }

    @Override
    public long getItemId(int position) { return position; }

    @Override
    public RankItemModel getItem(int position) { return listItems.get(position); }

    @Override
    public int getCount() {
        if (null == listItems) {
            return 0;
        }
        return listItems.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        RankItemModel item = listItems.get(position);
        final View view;
        final ViewHolder viewHolder;
        dbHelper = new MyDatabaseHelper(context, "Info.db", null, 1);
        db = dbHelper.getWritableDatabase();
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            viewHolder.tvCalorie = (TextView) view.findViewById(R.id.tvCalorie);
            viewHolder.tvId = (TextView) view.findViewById(R.id.tvId);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvName.setText(item.getName());
        viewHolder.tvCalorie.setText(item.getCalorie());
        viewHolder.tvId.setText(item.getId());

        return view;
    }

    class ViewHolder {
        TextView tvName;
        TextView tvCalorie;
        TextView tvId;
    }
}
