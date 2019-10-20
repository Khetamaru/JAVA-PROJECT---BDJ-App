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

public class stuffViewEnd extends Activity {

    User user;
    Game game;
    Date startDate;
    ObjectMapper mapper;
    Borrow borrow;

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

                game = mapper.readValue(response.body().string(), Game.class);
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

                    Toast.makeText(stuffViewEnd.this, "Can't go back to the future", Toast.LENGTH_LONG).show();
                }
                else {

                    borrow = new Borrow(0, user, game, startDate, dateText);

                    String req = "{" +
                                    "\"user\": {" +
                                                "\"idUser\": \"" + borrow.user.idUser + "\"," +
                                                "\"surname\": \"" + borrow.user.surname + "\"," +
                                                "\"login\": \"" + borrow.user.login + "\"," +
                                                "\"password\": \"" + borrow.user.password + "\"," +
                                                "\"mail\": \"" + borrow.user.mail + "\"," +
                                                "\"level\": \"" + borrow.user.level + "\"" +
                                                "}," +
                                    "\"game\": {" +
                                                "\"idGame\": \"" + borrow.game.idGame + "\"," +
                                                "\"name\": \"" + borrow.game.name + "\"," +
                                                "\"state\": \"" + borrow.game.state + "\"" +
                                                "}," +
                                    "\"startDate\": \"" + borrow.startDate.getTime() + "\"," +
                                    "\"endDate\": \"" + borrow.endDate.getTime() + "\"" +
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
                    });
                }
            }
        });
    }
}