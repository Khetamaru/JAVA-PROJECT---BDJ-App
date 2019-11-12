package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoaningAddEquipment extends Activity {

    User user;
    ListView listView;
    Context context;
    ObjectMapper mapper;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.loaning_add_equipment);

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user/" + getIntent().getIntExtra("idUser", 0))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("LoaningAddEquipment", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                user = mapper.readValue(response.body().string(), User.class);

                MediaType JSON
                        = MediaType.get("application/json; charset=utf-8");

                Request request = new Request.Builder()
                        .url("http://192.168.43.110:8080/equipment/validated")
                        .get()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        Log.e("LoaningAddEquipment", "fail", e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        Equipment[] equipments = new ObjectMapper().readValue(response.body().string(), Equipment[].class);

                        ArrayList<Equipment> equipmentsList = getListData(equipments);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                listView = (ListView) findViewById(R.id.equipmentListView);

                                listView.setAdapter(new Equipment_adapter(context, equipmentsList));

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> a, View v, int position, long textView_id) {
                                        Equipment equipment = (Equipment) listView.getItemAtPosition(position);
                                        Intent intent = new Intent(v.getContext(), LoaningAddNext.class);
                                        intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                        intent.putExtra("idEquipment", equipment.getIdEquipment());
                                        startActivity(intent);
                                    }
                                });

                                back = findViewById(R.id.back);
                                back.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        Intent intent;
                                        intent = new Intent(v.getContext(), LoaningPage.class);
                                        intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
                                        startActivity(intent);
                                    }
                                });
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
