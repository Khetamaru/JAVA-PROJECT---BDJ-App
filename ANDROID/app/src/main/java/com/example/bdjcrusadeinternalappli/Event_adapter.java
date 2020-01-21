package com.example.bdjcrusadeinternalappli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Event_adapter extends BaseAdapter {

    private ArrayList<Event> listData;
    private LayoutInflater layoutInflater;

    public Event_adapter(Context aContext, ArrayList<Event> listData) {

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

            v = layoutInflater.inflate(R.layout.event_list, null);
            holder = new Event_adapter.ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.name);
            holder.uPlace = (TextView) v.findViewById(R.id.place);
            holder.uStartDate = (TextView) v.findViewById(R.id.startDate);
            holder.uStartHour = (TextView) v.findViewById(R.id.startHour);
            holder.uEndDate = (TextView) v.findViewById(R.id.endDate);
            holder.uEndHour = (TextView) v.findViewById(R.id.endHour);
            v.setTag(holder);

        } else {

            holder = (Event_adapter.ViewHolder) v.getTag();
        }

        String[] startDate = listData.get(position).getStartDate().toString().split(" ");
        String[] endDate = listData.get(position).getEndDate().toString().split(" ");
        String[] startTime = listData.get(position).getStartHour().toString().split(":");
        String[] endTime = listData.get(position).getEndHour().toString().split(":");

        holder.uName.setText(listData.get(position).getName());
        holder.uPlace.setText(listData.get(position).getPlace());
        holder.uStartDate.setText(startDate[2] + " " + startDate[1] + " " + startDate[5]);
        holder.uEndDate.setText(endDate[2] + " " + endDate[1] + " " + endDate[5]);
        holder.uStartHour.setText(startTime[0] + ":" + startTime[1]);
        holder.uEndHour.setText(endTime[0] + ":" + endTime[1]);
        return v;
    }

    static class ViewHolder {
        TextView uName;
        TextView uPlace;
        TextView uStartHour;
        TextView uStartDate;
        TextView uEndHour;
        TextView uEndDate;
    }
}