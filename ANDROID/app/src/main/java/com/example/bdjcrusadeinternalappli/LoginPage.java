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
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginPage extends Activity {

    Button co;
    Button insc;
    EditText login;
    EditText passw;
    ObjectMapper mapper;

    //Login logs;
    //File logsJSON;
    //FileOutputStream logsStream;
    String logsString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LoginPage", ":)");

        mapper = new ObjectMapper();

        setContentView(R.layout.login_page);

        co = findViewById(R.id.connect);
        insc = findViewById(R.id.create);
        login = findViewById(R.id.login);
        passw = findViewById(R.id.password);

        co.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String log = login.getText().toString();
                String pass = passw.getText().toString();

                /*logsJSON = new File("./logs.json");
                try {
                    logs = mapper.readValue(logsJSON, Login.class);
                    login.setText(logs.getLog());
                    passw.setText(logs.getPassword());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    logsStream = new FileOutputStream("./logs.json");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/

                MediaType JSON
                        = MediaType.get("application/json; charset=utf-8");
                OkHttpClient client = new OkHttpClient();

                logsString = "{\"log\" : \"" + log + "\", \"password\" : \"" + pass + "\"}";

                RequestBody body = RequestBody.create(JSON, logsString);

                Request request = new Request.Builder()
                        .url("http://192.168.43.110:8080/user/login")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("LoginPage", "fail",e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        Log.i("LoginPage", "" + response.code());

                        if (response.code() == 200) {
                            Log.i("LoginPage", "après 200");
                            User user = mapper.readValue(response.body().string(), User.class);
                            //mapper.writeValue(logsStream, logs);
                            Log.i("LoginPage", "après writeValue");

                           runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(v.getContext(), MainPage.class);
                                    intent.putExtra("idUser", user.idUser);
                                    startActivity(intent);
                                }
                            });
                        }
                        else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(LoginPage.this, "Connection failed -- Login : " + log + " Password : " + pass, Toast.LENGTH_LONG).show();
                                    Log.i("LoginPage", "wrong try");
                                }
                            });
                        }
                    }
                });
            }
        });

        insc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateAccountPage.class);
                startActivity(intent);
            }
        });
    }

    protected boolean verifConnect(String login, String password) {

        if (login.equals("oui") && password.equals("non")) {

            return true;
        }
        else {

            return false;
        }
    }
}
