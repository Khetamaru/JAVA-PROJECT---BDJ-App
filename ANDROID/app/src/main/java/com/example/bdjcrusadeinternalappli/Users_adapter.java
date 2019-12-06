package com.example.bdjcrusadeinternalappli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Users_adapter extends BaseAdapter {

    private ArrayList<User> listData;
    private LayoutInflater layoutInflater;

    public Users_adapter(Context aContext, ArrayList<User> listData) {

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

            v = layoutInflater.inflate(R.layout.users_list, null);
            holder = new ViewHolder();
            holder.uSurname = (TextView) v.findViewById(R.id.surname);
            holder.uLevel = (TextView) v.findViewById(R.id.level);
            v.setTag(holder);

        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.uSurname.setText(listData.get(position).getSurname());
        holder.uLevel.setText(listData.get(position).getLevel());
        return v;
    }

    static class ViewHolder {
        TextView uSurname;
        TextView uLevel;
    }
}