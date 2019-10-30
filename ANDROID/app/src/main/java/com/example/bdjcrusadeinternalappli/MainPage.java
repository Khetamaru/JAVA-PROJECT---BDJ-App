package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainPage extends Activity {

    TextView textView_id;
    TextView textView_surname;
    TextView textView_level;

    Button button_inventory;
    Button button_historic;
    Button button_loaning;
    Button button_location;
    Button button_levelUp;
    Button button_logOut;

    ObjectMapper mapper;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainPage", "creation");

        setContentView(R.layout.main_page);

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user/" + getIntent().getIntExtra("idUser",0))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("MainPage", "fail",e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                user = mapper.readValue(response.body().string(), User.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        levelChoice();
                        //textView_id.setText(String.valueOf(user.idUser));
                        textView_surname = findViewById(R.id.surname);
                        textView_surname.setText(user.surname);
                        /*editText_login.setText(user.editText_login);
                        editText_password.setText(user.editText_password);
                        editText_mail.setText(user.editText_mail);*/

                        button_logOut = findViewById(R.id.disconnection);
                        button_logOut.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                logOut(v);
                            }
                        });
                    }
                });
            }
        });
    }

    protected void levelChoice() {

        switch (user.level) {

            case("student"):

            case("cotisant"):

                setContentView(R.layout.main_page_student);
                button_inventory = findViewById(R.id.inventory);
                button_inventory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToInventory(v);
                    }
                });

                button_loaning = findViewById(R.id.loaning);
                button_loaning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToBorrow(v);
                    }
                });

                button_location = findViewById(R.id.location);
                button_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLocation(v);
                    }
                });

                button_levelUp = findViewById(R.id.levelUp);
                button_levelUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLevelUp(v);
                    }
                });

                textView_level = findViewById(R.id.level);
                textView_level.setText(user.level);
                break;

            case("bdjMember"):

            case("admin"):

                setContentView(R.layout.main_page_bdj_member);
                button_inventory = findViewById(R.id.inventory);
                button_inventory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToInventory(v);
                    }
                });

                button_loaning = findViewById(R.id.loaning);
                button_loaning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToBorrow(v);
                    }
                });

                button_location = findViewById(R.id.location);
                button_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLocation(v);
                    }
                });

                button_historic = findViewById(R.id.historic);
                button_historic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToHistoric(v);
                    }
                });
                break;

        }
    }

    protected void goToInventory(View v) {

        Intent intent = new Intent(v.getContext(), InventoryView.class);
        intent.putExtra("idUser", user.idUser);
        startActivity(intent);
    }

    protected void goToBorrow(View v) {

        Intent intent = new Intent(v.getContext(), LoaningView.class);
        intent.putExtra("idUser", user.idUser);
        startActivity(intent);
    }

    protected void goToLocation(View v) {

        Intent intent = new Intent(v.getContext(), LocationView.class);
        intent.putExtra("idUser", user.idUser);
        startActivity(intent);
    }

    protected void goToHistoric(View v) {

        Intent intent = new Intent(v.getContext(), UserHistoricView.class);
        intent.putExtra("idUser", user.idUser);
        startActivity(intent);
    }

    protected void goToLevelUp(View v) {

        OkHttpClient client = new OkHttpClient();
        String stringBody = "{}";

        MediaType JSON
                = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, stringBody);

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user/levelUp/" + user.idUser)
                .patch(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Log.e("MainPage", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Intent intent = new Intent(v.getContext(), levelUpView.class);
                intent.putExtra("idUser", user.idUser);
                startActivity(intent);
            }
        });
    }

    protected void logOut(View v) {

        Intent intent = new Intent(v.getContext(), LoginPage.class);
        startActivity(intent);
    }
}
