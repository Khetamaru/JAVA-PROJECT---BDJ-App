package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InventoryView extends Activity {

    ObjectMapper mapper;
    ListView listView;
    ArrayAdapter<Equipment> arrayAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/game")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Log.e("InventoryView", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Equipment[] equipment = new ObjectMapper().readValue(response.body().string(), Equipment[].class);

                ArrayList<Equipment> gamesList = getListData(equipment);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(R.layout.inventory);

                        listView = (ListView) findViewById(R.id.InventoryListView);

                        listView.setAdapter(new inventory_adapter(context, gamesList));

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Equipment equipment = (Equipment) listView.getItemAtPosition(position);
                                Intent intent = new Intent(v.getContext(), EquipmentDetail.class);
                                intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                intent.putExtra("idGame", equipment.idEquipment);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    private ArrayList getListData(Equipment[] equipments) {
        ArrayList<Equipment> results = new ArrayList<>();

        for (Equipment equipment : equipments) {

            results.add(equipment);
        }

        return results;
    }
}
