package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LocationEndView extends Activity {

    CalendarView endDate;
    Button next;
    User user;
    String place;
    Date startDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.location_end_view);

        endDate = findViewById(R.id.date);

        place = getIntent().getStringExtra("place");

        next = findViewById(R.id.valid);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ObjectMapper mapper = new ObjectMapper();

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

                        startDate = new Date(getIntent().getLongExtra("startDate", 0));

                        Date dateText = new Date(endDate.getDate());

                        String req = "{" +
                                        "\"user\": {" +
                                                    "\"idUser\": \"" + user.idUser + "\"," +
                                                    "\"editText_surname\": \"" + user.surname + "\"," +
                                                    "\"editText_login\": \"" + user.login + "\"," +
                                                    "\"editText_password\": \"" + user.password + "\"," +
                                                    "\"editText_mail\": \"" + user.mail + "\"," +
                                                    "\"textView_level\": \"" + user.level + "\"" +
                                        "}," +
                                        "\"place\": \"" + place + "\"," +
                                        "\"startDate\": \"" + startDate.getTime() + "\"," +
                                        "\"endDate\": \"" + dateText.getTime() + "\"" +
                                "}";

                        MediaType JSON = MediaType.get("application/json; charset=utf-8");

                        RequestBody body = RequestBody.create(JSON, req);

                        OkHttpClient client = new OkHttpClient();

                        Request request = new Request.Builder()
                                .url("http://192.168.43.110:8080/member")
                                .put(body)
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("LoginPage", "fail", e);
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                Log.i("LoginPage", "" + response.code());

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        Intent intent = new Intent(v.getContext(), LocationView.class);
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