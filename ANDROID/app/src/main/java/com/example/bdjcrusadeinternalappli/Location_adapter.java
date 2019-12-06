package com.example.bdjcrusadeinternalappli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Location_adapter extends BaseAdapter {

    private ArrayList<Location> listData;
    private LayoutInflater layoutInflater;

    public Location_adapter(Context aContext, ArrayList<Location> listData) {

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

            v = layoutInflater.inflate(R.layout.location_list, null);
            holder = new ViewHolder();
            holder.uUser = (TextView) v.findViewById(R.id.user);
            holder.uPlace = (TextView) v.findViewById(R.id.place);
            holder.uDate = (TextView) v.findViewById(R.id.date);
            holder.uHour = (TextView) v.findViewById(R.id.hour);
            v.setTag(holder);

        } else {

            holder = (ViewHolder) v.getTag();
        }

        String[] date = listData.get(position).getDate().toString().split(" ");
        String[] startTime = listData.get(position).getStartHour().toString().split(":");
        String[] endTime = listData.get(position).getEndHour().toString().split(":");

        holder.uUser.setText(listData.get(position).user.getSurname());
        holder.uPlace.setText(listData.get(position).getPlace());
        holder.uDate.setText(date[2] + " " + date[1] + " " + date[5]);
        holder.uHour.setText(startTime[0] + ":" + startTime[1] + " / " + endTime[0] + ":" + endTime[1]);
        return v;
    }

    static class ViewHolder {
        TextView uUser;
        TextView uPlace;
        TextView uHour;
        TextView uDate;
    }
}