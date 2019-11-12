package com.example.bdjcrusadeinternalappli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserHistoric_adapter extends BaseAdapter {

    private ArrayList<UserHistoric> listData;
    private LayoutInflater layoutInflater;

    public UserHistoric_adapter(Context aContext, ArrayList<UserHistoric> listData) {

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

            v = layoutInflater.inflate(R.layout.user_historic_list, null);
            holder = new ViewHolder();
            holder.uSurname = (TextView) v.findViewById(R.id.surname);
            holder.uDate = (TextView) v.findViewById(R.id.date);
            v.setTag(holder);

        } else {

            holder = (ViewHolder) v.getTag();
        }

        String[] date = listData.get(position).getDate().toString().split(":");

        holder.uSurname.setText(listData.get(position).getSurname());
        holder.uDate.setText(date[0] + " " + date[1] + " " + date[2]);
        return v;
    }

    static class ViewHolder {
        TextView uSurname;
        TextView uDate;
    }
}