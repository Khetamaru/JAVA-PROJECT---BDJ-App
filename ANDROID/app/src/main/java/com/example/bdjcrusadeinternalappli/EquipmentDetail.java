package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EquipmentDetail extends Activity {

    User user;
    Equipment equipment;
    ObjectMapper mapper;

    CalendarView date;
    Button valid;
    Date dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stuff_view_start);

        OkHttpClient client = new OkHttpClient();
        mapper = new ObjectMapper();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user/" + getIntent().getIntExtra("idUser",0))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("LoginPage", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                user = mapper.readValue(response.body().string(), User.class);
            }
        });

        mapper = new ObjectMapper();

        request = new Request.Builder()
                .url("http://192.168.43.110:8080/game/" + getIntent().getIntExtra("idGame",0))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("LoginPage", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                equipment = mapper.readValue(response.body().string(), Equipment.class);
            }
        });

        date = findViewById(R.id.date);
        valid = findViewById(R.id.valid);
        valid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*mapper = new ObjectMapper();

                MediaType JSON
                        = MediaType.get("application/json; charset=utf-8");

                String stringRequest = "{\"date\" : \"" + dateText + "\"}";

                RequestBody body = RequestBody.create(JSON, stringRequest);

                Request request = new Request.Builder()
                        .url("http://192.168.43.110:8080/borrow/startDate")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        Log.e("LoginPage", "fail", e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        Log.i("EquipmentDetail", "" + response.code());

                        if (response.code() == 404) {

                            */Intent intent = new Intent(v.getContext(), EquipmentDetailEnd.class);
                            intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                            intent.putExtra("idGame", equipment.getIdEquipment());
                            intent.putExtra("startDate", date.getDate());
                            startActivity(intent);
                        /*}
                        else if (response.code() == 200) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(EquipmentDetail.this, "Wrong Hour", Toast.LENGTH_LONG).show();
                                    Log.i("LoginPage", "wrong try");
                                }
                            });
                        }
                        else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(EquipmentDetail.this, "Strange Error", Toast.LENGTH_LONG).show();
                                    Log.i("LoginPage", "wrong try");
                                }
                            });
                        }
                    }
                });*/
            }
        });
    }
}
