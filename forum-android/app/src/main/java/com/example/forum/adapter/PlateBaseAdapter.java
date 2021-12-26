package com.example.forum.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.forum.R;
import com.example.forum.model.pojo.Plate;
import com.example.forum.ui.post.PlatePostActivity;

import java.util.List;

public class PlateBaseAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

    private Context context;
    private List<Plate> list;

    public PlateBaseAdapter(Context context, List<Plate> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.plate_item, null);
            holder.tv_plate_name = view.findViewById(R.id.tv_plate_name);
            holder.tv_plate_count = view.findViewById(R.id.tv_plate_count);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        Plate plate = list.get(i);
        holder.tv_plate_name.setText(plate.getName());
        holder.tv_plate_count.setText(String.valueOf(plate.getPostCount()));
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Plate plate = list.get(i);
        Bundle bundle = new Bundle();
        bundle.putLong("plateId", plate.getPlateId());
        Intent intent = new Intent(context, PlatePostActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public static final class ViewHolder {
        public TextView tv_plate_name;
        public TextView tv_plate_count;
    }
}
