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

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainPage extends Activity {

    TextView id;
    TextView surname;
    TextView login;
    TextView password;
    TextView mail;

    Button inventory;
    Button historic;
    Button borrow;
    Button location;
    Button levelUp;

    ObjectMapper mapper;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LoginPage", ":)");

        setContentView(R.layout.main_page);

        mapper = new ObjectMapper();

        /*id = findViewById(R.id.id);
        surname = findViewById(R.id.surname);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        mail = findViewById(R.id.mail);*/

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user/" + getIntent().getIntExtra("idUser",0))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("LoginPage", "fail",e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                User user = mapper.readValue(response.body().string(), User.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //id.setText(String.valueOf(user.idUser));
                        surname.setText(user.surname);
                        /*login.setText(user.login);
                        password.setText(user.password);
                        mail.setText(user.mail);*/
                    }
                });
            }
        });
    }

    protected void levelChoice() {

        switch (user.level) {

            case("student"):

                setContentView(R.layout.main_page_student);
                inventory = findViewById(R.id.inventory);
                inventory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToInventory(v);
                    }
                });

                borrow = findViewById(R.id.borrow);
                borrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToBorrow(v);
                    }
                });

                location = findViewById(R.id.location);
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLocation(v);
                    }
                });

                levelUp = findViewById(R.id.levelUp);
                levelUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLevelUp(v);
                    }
                });

            case("cotisant"):

                setContentView(R.layout.main_page_student);
                inventory = findViewById(R.id.inventory);
                inventory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToInventory(v);
                    }
                });

                borrow = findViewById(R.id.borrow);
                borrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToBorrow(v);
                    }
                });

                location = findViewById(R.id.location);
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLocation(v);
                    }
                });

                levelUp = findViewById(R.id.levelUp);
                levelUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLevelUp(v);
                    }
                });

            case("bdjMember"):

                setContentView(R.layout.main_page_student);
                inventory = findViewById(R.id.inventory);
                inventory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToInventory(v);
                    }
                });

                borrow = findViewById(R.id.borrow);
                borrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToBorrow(v);
                    }
                });

                location = findViewById(R.id.location);
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLocation(v);
                    }
                });

                historic = findViewById(R.id.historic);
                historic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToHistoric(v);
                    }
                });

            case("admin"):

                setContentView(R.layout.main_page_student);
                inventory = findViewById(R.id.inventory);
                inventory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToInventory(v);
                    }
                });

                borrow = findViewById(R.id.borrow);
                borrow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToBorrow(v);
                    }
                });

                location = findViewById(R.id.location);
                location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLocation(v);
                    }
                });

                historic = findViewById(R.id.historic);
                historic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToHistoric(v);
                    }
                });
        }
    }

    protected void goToInventory(View v) {

        Intent intent = new Intent(v.getContext(), InventoryView.class);
        intent.putExtra("idUser", user.idUser);
        startActivity(intent);
    }

    protected void goToBorrow(View v) {

        Intent intent = new Intent(v.getContext(), BorrowView.class);
        intent.putExtra("idUser", user.idUser);
        startActivity(intent);
    }

    protected void goToLocation(View v) {

        Intent intent = new Intent(v.getContext(), LocationView.class);
        intent.putExtra("idUser", user.idUser);
        startActivity(intent);
    }

    protected void goToHistoric(View v) {

        Intent intent = new Intent(v.getContext(), HistoricView.class);
        intent.putExtra("idUser", user.idUser);
        startActivity(intent);
    }

    protected void goToLevelUp(View v) {

    }

    protected void disconected() {

    }
}
