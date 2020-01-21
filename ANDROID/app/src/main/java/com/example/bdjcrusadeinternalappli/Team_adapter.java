package com.example.bdjcrusadeinternalappli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Team_adapter extends BaseAdapter {

    private ArrayList<Team> listData;
    private LayoutInflater layoutInflater;

    public Team_adapter(Context aContext, ArrayList<Team> listData) {

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

            v = layoutInflater.inflate(R.layout.equipment_list, null);
            holder = new ViewHolder();
            holder.uTeamName = (TextView) v.findViewById(R.id.equipmentName);
            holder.uTeamSize = (TextView) v.findViewById(R.id.ableToBorrow);
            v.setTag(holder);

        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.uTeamName.setText(listData.get(position).getName());
        holder.uTeamSize.setText("Size: " + listData.get(position).getUsers().length);

        return v;
    }

    static class ViewHolder {
        TextView uTeamName;
        TextView uTeamSize;
    }
}