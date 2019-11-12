package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LocationDetail extends Activity {

    Context context;
    ObjectMapper mapper;

    User user;
    Location location;

    TextView userText;
    TextView placeText;
    TextView dateText;
    TextView startHourText;
    TextView endHourText;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.location_details);

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

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

                mapper = new ObjectMapper();

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://192.168.43.110:8080/location/" + getIntent().getIntExtra("idLocation", 0))
                        .get()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("LoginPage", "fail", e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        location = mapper.readValue(response.body().string(), Location.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                userText = findViewById(R.id.user);
                                userText.setText(user.getSurname());

                                placeText = findViewById(R.id.place);
                                placeText.setText(location.getPlace());

                                dateText = findViewById(R.id.date);
                                dateText.setText(location.getDate().toString());

                                String[] startTime = location.getStartHour().toString().split(":");
                                startHourText = findViewById(R.id.startHour);
                                startHourText.setText(startTime[0] + ":" + startTime[1]);

                                String[] endTime = location.getEndHour().toString().split(":");
                                endHourText = findViewById(R.id.endHour);
                                endHourText.setText(endTime[0] + ":" + endTime[1]);

                                back = findViewById(R.id.back);
                                back.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        Intent intent;
                                        intent = new Intent(v.getContext(), LocationView.class);
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
}