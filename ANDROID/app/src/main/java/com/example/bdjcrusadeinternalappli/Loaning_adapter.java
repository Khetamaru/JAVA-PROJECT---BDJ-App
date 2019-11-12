package com.example.bdjcrusadeinternalappli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Loaning_adapter extends BaseAdapter {

    private ArrayList<Loaning> listData;
    private LayoutInflater layoutInflater;

    public Loaning_adapter(Context aContext, ArrayList<Loaning> listData) {

        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {

        return listData.size();
    }
    @Override
    public Object getItem(int position) {

        return listData.get(position);
    }
    @Override
    public long getItemId(int position) {

        return position;
    }

    public View getView(int position, View v, ViewGroup vg) {

        ViewHolder holder;

        if (v == null) {

            v = layoutInflater.inflate(R.layout.loaning_list, null);
            holder = new ViewHolder();
            holder.uUserName = (TextView) v.findViewById(R.id.userName);
            holder.uEquipmentName = (TextView) v.findViewById(R.id.equipmentName);
            v.setTag(holder);

        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.uUserName.setText(listData.get(position).user.getSurname());
        holder.uEquipmentName.setText(listData.get(position).equipment.getName());
        return v;
    }

    static class ViewHolder {
        TextView uUserName;
        TextView uEquipmentName;
    }
}