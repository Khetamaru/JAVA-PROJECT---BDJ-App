package com.example.bdjcrusadeinternalappli;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class inventory_adapter extends BaseAdapter {

    private ArrayList<Game> listData;
    private LayoutInflater layoutInflater;

    public inventory_adapter(Context aContext, ArrayList<Game> listData) {

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

            v = layoutInflater.inflate(R.layout.inventory_valid, null);
            holder = new ViewHolder();
            holder.uName = (TextView) v.findViewById(R.id.name);
            holder.uState = (TextView) v.findViewById(R.id.state);
            v.setTag(holder);

        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.uName.setText(listData.get(position).getName());
        holder.uState.setText(listData.get(position).getState());
        return v;
    }

    static class ViewHolder {
        TextView uName;
        TextView uState;
    }
}
