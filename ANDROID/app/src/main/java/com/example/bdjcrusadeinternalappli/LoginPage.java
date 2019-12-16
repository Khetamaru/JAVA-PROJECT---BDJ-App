package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import kotlinClass.RequestService;
import kotlinClass.RooterService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginPage extends Activity {

    Button button_connection;
    Button button_validation;
    EditText editText_login;
    EditText editText_password;
    ObjectMapper mapper;

    String stringRequest;

    RequestService requestService;
    RooterService rooterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Log.i("LoginPage", "Login Page button_creation");

        mapper = new ObjectMapper();

        setContentView(R.layout.login_page);

        button_connection = findViewById(R.id.connect);
        button_validation = findViewById(R.id.create);
        editText_login = findViewById(R.id.login);
        editText_password = findViewById(R.id.password);

        button_connection.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String login = editText_login.getText().toString();
                String password = editText_password.getText().toString();

                stringRequest = "{\"log\" : \"" + login + "\", \"password\" : \"" + password + "\"}";

                requestService.requestBuilderPost("user/login", stringRequest)
                        .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        Toast.makeText(LoginPage.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        Log.i("LoginPage", "response : " + response.code());

                        if (response.code() == 200) {

                            User user = mapper.readValue(response.body().string(), User.class);

                           runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    rooterService.changeActivity(new Intent(v.getContext(), MainPage.class), LoginPage.this, user.idUser);
                                }
                            });
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(LoginPage.this, "Wrong login or password", Toast.LENGTH_LONG).show();
                                    Log.i("LoginPage", "wrong try");
                                }
                            });
                        }
                    }
                });
            }
        });

        button_validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), CreateAccountPage.class);
                startActivity(intent);
            }
        });
    }
}
