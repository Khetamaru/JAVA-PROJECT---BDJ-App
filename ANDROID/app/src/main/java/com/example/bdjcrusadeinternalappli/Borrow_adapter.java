package com.example.bdjcrusadeinternalappli;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;

public class Borrow_adapter extends BaseAdapter {

    private ArrayList<Borrow> listData;
    private LayoutInflater layoutInflater;

    public Borrow_adapter(Context aContext, ArrayList<Borrow> listData) {

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

            v = layoutInflater.inflate(R.layout.borrow_list, null);
            holder = new ViewHolder();
            holder.uUserName = (TextView) v.findViewById(R.id.userName);
            holder.uGameName = (TextView) v.findViewById(R.id.gameName);
            holder.uStartDate = (TextView) v.findViewById(R.id.startDate);
            holder.uEndDate = (TextView) v.findViewById(R.id.endDate);
            v.setTag(holder);

        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.uUserName.setText(listData.get(position).user.getSurname());
        holder.uGameName.setText(listData.get(position).game.getName());
        String startDate = listData.get(position).getStartDate().toString();
        holder.uStartDate.setText(startDate);
        String endDate = listData.get(position).getEndDate().toString();
        holder.uEndDate.setText(endDate);
        return v;
    }

    static class ViewHolder {
        TextView uUserName;
        TextView uGameName;
        TextView uStartDate;
        TextView uEndDate;
    }
}