package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import kotlinClass.RequestService;
import kotlinClass.RooterService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserHistoricDetail extends Activity {

    User user;
    UserHistoric userHistoric;
    ObjectMapper mapper;

    TextView surname;
    TextView level;
    TextView mail;
    TextView login;
    TextView password;
    TextView date;

    Button back;

    RequestService requestService;
    RooterService rooterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loading_page);

        OkHttpClient client = new OkHttpClient();

        mapper = new ObjectMapper();

        requestService.requestBuilderGet("historic", getIntent().getIntExtra("idUserHistoric",0))
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(UserHistoricDetail.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                userHistoric = mapper.readValue(response.body().string(), UserHistoric.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(R.layout.user_historic_details);

                        surname = findViewById(R.id.surname);
                        surname.setText(userHistoric.getSurname());

                        level = findViewById(R.id.level);
                        level.setText(userHistoric.getLevel());

                        mail = findViewById(R.id.mail);
                        mail.setText(userHistoric.getMail());

                        login = findViewById(R.id.login);
                        login.setText(userHistoric.getLogin());

                        password = findViewById(R.id.password);
                        password.setText(userHistoric.getPassword());

                        date = findViewById(R.id.date);
                        String[] dateTab = userHistoric.getDate().toString().split(" ");
                        date.setText(dateTab[2] + " " + dateTab[1] + " " + dateTab[5]);

                        back = findViewById(R.id.back);
                        back.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                rooterService.changeActivity(new Intent(v.getContext(), UserHistoricView.class), UserHistoricDetail.this, getIntent().getIntExtra("idUser", 0));
                            }
                        });
                    }
                });
            }
        });
    }
}
