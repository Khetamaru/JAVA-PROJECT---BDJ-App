package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateAccountPage extends Activity {

    EditText surname;
    EditText mail;
    EditText login;
    EditText password;
    EditText confirmPassword;
    Button creation;

    OkHttpClient client;
    MediaType JSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LoginPage", ":)");

        OkHttpClient client = new OkHttpClient();
        JSON = MediaType.get("application/json; charset=utf-8");

        setContentView(R.layout.create_account_page);

        surname = findViewById(R.id.pseudo);
        mail = findViewById(R.id.mail);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        creation = findViewById(R.id.submit);

        creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verifSurname()) {

                    if (verifMail()) {

                        if (verifPassword()) {

                            if (verifConfirmPassword()) {

                                verifLogin();
                            }
                        }
                    }
                }
            }
        });
    }

    protected boolean verifSurname() {

        if (surname.length() > 3) {

            return true;
        }
        else {

            Toast.makeText(CreateAccountPage.this, "Surname to short", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected boolean verifMail() {

        if (!mail.equals("")) {

            return true;
        }
        else {

            Toast.makeText(CreateAccountPage.this, "You have to write a mail address", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected void verifLogin() {

        if (login.length() > 3) {

            if (login.length() < 15) {

                String req = "{\"login\" : \"" + login + "\"";

                RequestBody body = RequestBody.create(JSON, req);

                Request request = new Request.Builder()
                        .url("http://192.168.43.110:8080/user/findByLogin")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("CreateAccountPage", "fail", e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        Log.i("CreateAccountPage", "" + response.code());

                        if (response.code() == 200) {

                            Log.i("CreateAccountPage", "this account already exist");

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(CreateAccountPage.this, "This Login is already take", Toast.LENGTH_LONG).show();
                                }
                            });

                        } else if (response.code() == 401) {

                            createAccount();

                        } else {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    Toast.makeText(CreateAccountPage.this, "Strange Error", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
            else {

                Toast.makeText(CreateAccountPage.this, "Login to long (MAX 14)", Toast.LENGTH_LONG).show();
            }
        }
        else {

            Toast.makeText(CreateAccountPage.this, "Login to short (MIN 4)", Toast.LENGTH_LONG).show();
        }
    }

    protected boolean verifPassword() {

        if (password.length() > 3) {

            if (password.length() < 15) {

                return true;
            }
            else {

                Toast.makeText(CreateAccountPage.this, "Password to long (MAX 14)", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        else {

            Toast.makeText(CreateAccountPage.this, "Password to short (MIN 4)", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected boolean verifConfirmPassword() {

        if (password == confirmPassword) {

            return true;
        }
        else {

            Toast.makeText(CreateAccountPage.this, "Password badly copy", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected void createAccount() {

        goToMainPage();
    }

    protected void goToMainPage() {

        /*MediaType JSON
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
        });*/
    }
}
