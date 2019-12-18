package com.example.bdjcrusadeinternalappli;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BoardGames_adapter extends BaseAdapter {

    private ArrayList<BoardGame> listData;
    private LayoutInflater layoutInflater;

    public BoardGames_adapter(Context aContext, ArrayList<BoardGame> listData) {

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
            holder.uEquipment = (TextView) v.findViewById(R.id.equipmentName);
            holder.uAble = (TextView) v.findViewById(R.id.ableToBorrow);
            v.setTag(holder);

        } else {

            holder = (ViewHolder) v.getTag();
        }

        holder.uEquipment.setText(listData.get(position).getName());
        String ableToBorrow = listData.get(position).getAbleToBorrow();
        if(ableToBorrow.equals("yes")) {

            holder.uAble.setText("borrowUp");
        }
        else {

            holder.uAble.setText("borrowDown");
        }

        return v;
    }

    static class ViewHolder {
        TextView uEquipment;
        TextView uAble;
    }
}