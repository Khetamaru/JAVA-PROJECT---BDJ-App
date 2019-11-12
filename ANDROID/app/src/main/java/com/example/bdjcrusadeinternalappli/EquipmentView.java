package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class EquipmentView extends Activity {

    ObjectMapper mapper;
    ListView listView;
    ArrayAdapter<Equipment> arrayAdapter;
    Context context;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.inventory);

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/equipment")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Log.e("EquipmentView", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Equipment[] equipment = new ObjectMapper().readValue(response.body().string(), Equipment[].class);

                ArrayList<Equipment> equipmentsList = getListData(equipment);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        listView = (ListView) findViewById(R.id.equipmentListView);

                        listView.setAdapter(new Equipment_adapter(context, equipmentsList));

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Equipment equipment = (Equipment) listView.getItemAtPosition(position);
                                Intent intent = new Intent(v.getContext(), EquipmentDetail.class);
                                intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                intent.putExtra("idEquipment", equipment.idEquipment);
                                startActivity(intent);
                            }
                        });

                        back = findViewById(R.id.back);
                        back.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                Intent intent;
                                intent = new Intent(v.getContext(), MainPage.class);
                                intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
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
