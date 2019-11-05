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
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class EquipmentDetailEnd extends Activity {

    User user;
    Equipment equipment;
    Date startDate;
    ObjectMapper mapper;
    Loaning loaning;

    CalendarView date;
    Button valid;
    Date dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stuff_view_end);

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

                startDate = new Date(getIntent().getLongExtra("startDate", 0));
                Log.i("EndDate", "" + startDate);

                dateText = new Date(date.getDate());

                if (startDate.getTime() > dateText.getTime()) {

                    Toast.makeText(EquipmentDetailEnd.this, "Can't go back to the future", Toast.LENGTH_LONG).show();
                }
                else {

                    /*loaning_admin = new Loaning(0, user, equipment, startDate, dateText);

                    String req = "{" +
                                    "\"user\": {" +
                                                "\"idUser\": \"" + loaning_admin.user.idUser + "\"," +
                                                "\"editText_surname\": \"" + loaning_admin.user.surname + "\"," +
                                                "\"editText_login\": \"" + loaning_admin.user.login + "\"," +
                                                "\"editText_password\": \"" + loaning_admin.user.password + "\"," +
                                                "\"editText_mail\": \"" + loaning_admin.user.mail + "\"," +
                                                "\"textView_level\": \"" + loaning_admin.user.level + "\"" +
                                                "}," +
                                    "\"equipment\": {" +
                                                "\"idGame\": \"" + loaning_admin.equipment.idGame + "\"," +
                                                "\"name\": \"" + loaning_admin.equipment.name + "\"," +
                                                "\"state\": \"" + loaning_admin.equipment.state + "\"" +
                                                "}," +
                                    "\"startDate\": \"" + loaning_admin.startDate.getTime() + "\"," +
                                    "\"endDate\": \"" + loaning_admin.endDate.getTime() + "\"" +
                                "}";

                    MediaType JSON = MediaType.get("application/json; charset=utf-8");

                    RequestBody body = RequestBody.create(JSON, req);

                    OkHttpClient client = new OkHttpClient();

                    Request request = new Request.Builder()
                            .url("http://192.168.43.110:8080/borrow")
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

                                    Intent intent = new Intent(v.getContext(), MainPage.class);
                                    intent.putExtra("idUser", user.idUser);
                                    startActivity(intent);
                                }
                            });
                        }
                    });*/
                }
            }
        });
    }
}