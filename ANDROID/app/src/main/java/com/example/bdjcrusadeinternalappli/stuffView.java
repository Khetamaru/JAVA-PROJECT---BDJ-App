package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static java.text.DateFormat.SHORT;

public class stuffView extends Activity {

    User user;
    Game game;
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

                game = mapper.readValue(response.body().string(), Game.class);
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

                String logsString = "{\"date\" : \"" + dateText + "\"}";

                RequestBody body = RequestBody.create(JSON, logsString);

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

                        Log.i("stuffView", "" + response.code());

                        if (response.code() == 404) {

                            */Intent intent = new Intent(v.getContext(), stuffViewEnd.class);
                            intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                            intent.putExtra("idGame", game.idGame);
                            intent.putExtra("startDate", date.getDate());
                            startActivity(intent);
                        /*}
                        else if (response.code() == 200) {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(stuffView.this, "Wrong Hour", Toast.LENGTH_LONG).show();
                                    Log.i("LoginPage", "wrong try");
                                }
                            });
                        }
                        else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(stuffView.this, "Strange Error", Toast.LENGTH_LONG).show();
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
