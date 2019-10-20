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

    ObjectMapper mapper;

    OkHttpClient client;
    MediaType JSON;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LoginPage", ":)");

        OkHttpClient client = new OkHttpClient();
        JSON = MediaType.get("application/json; charset=utf-8");

        mapper = new ObjectMapper();

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

                                verifLogin(v);
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

    protected void verifLogin(View v) {

        if (login.length() > 3) {

            if (login.length() < 15) {

                System.out.println(login.getText().toString());

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://192.168.43.110:8080/user/byLogin/" + login.getText().toString())
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

                        } else if (response.code() == 404) {

                            createAccount(v);

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

        System.out.println(password.getText());
        System.out.println(confirmPassword.getText());

        if (password.getText().toString().equals(confirmPassword.getText().toString())) {

            return true;
        }
        else {

            Toast.makeText(CreateAccountPage.this, "Password badly copy", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected void createAccount(View v) {

        String req =    "{" +
                "\"surname\" : \"" + surname.getText().toString() + "\"," +
                " \"login\" : \"" + login.getText().toString() + "\"," +
                " \"password\" : \"" + password.getText().toString() + "\"," +
                " \"mail\" : \"" + mail.getText().toString() + "\"," +
                " \"level\" : \"student\"" +
                "}";

        RequestBody body = RequestBody.create(JSON, req);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user")
                .put(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("LoginPage", "fail",e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Log.i("LoginPage", "" + response.code());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        goToMainPage(v);
                    }
                });
            }
        });
    }

    protected void goToMainPage(View v) {

        String req = "{\"login\" : \"" + login.getText().toString() + "\"";

        RequestBody body = RequestBody.create(JSON, req);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user/byLogin/" + login.getText().toString())
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

                    User user = mapper.readValue(response.body().string(), User.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Intent intent = new Intent(v.getContext(), MainPage.class);
                            intent.putExtra("idUser", user.idUser);
                            startActivity(intent);
                        }
                    });
                }
            }
        });
    }
}
