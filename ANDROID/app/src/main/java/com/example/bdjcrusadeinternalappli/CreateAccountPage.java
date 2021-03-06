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

public class CreateAccountPage extends Activity {

    EditText editText_surname;
    EditText editText_mail;
    EditText editText_login;
    EditText editText_password;
    EditText editText_confirm_password;
    Button button_creation;

    ObjectMapper mapper;

    RequestService requestService = new RequestService();
    RooterService rooterService = new RooterService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("CreateAccountPage", "creation");

        mapper = new ObjectMapper();

        setContentView(R.layout.create_account_page);

        editText_surname = findViewById(R.id.pseudo);

        editText_mail = findViewById(R.id.mail);

        editText_login = findViewById(R.id.login);

        editText_password = findViewById(R.id.password);

        editText_confirm_password = findViewById(R.id.confirmPassword);

        button_creation = findViewById(R.id.submit);

        button_creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verifSurname()) {

                    if (verificationMail()) {

                        if (verificationPassword()) {

                            if (verificationConfirmPassword()) {

                                verificationLogin(v);
                            }
                        }
                    }
                }
            }
        });
    }

    protected boolean verifSurname() {

        if (editText_surname.length() > 3) {

            return true;
        }
        else {

            Toast.makeText(CreateAccountPage.this, "Surname to short", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected boolean verificationMail() {

        if (!editText_mail.equals("")) {

            return true;
        }
        else {

            Toast.makeText(CreateAccountPage.this, "You have to write a editText_mail address", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected void verificationLogin(View v) {

        if (editText_login.length() > 3) {

            if (editText_login.length() < 15) {

                requestService.requestBuilderGet("user/byLogin", editText_login.getText().toString())
                        .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(CreateAccountPage.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        Log.i("CreateAccountPage", "response : " + response.code());

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

    protected boolean verificationPassword() {

        if (editText_password.length() > 3) {

            if (editText_password.length() < 15) {

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

    protected boolean verificationConfirmPassword() {

        if (editText_password.getText().toString().equals(editText_confirm_password.getText().toString())) {

            return true;
        }
        else {

            Toast.makeText(CreateAccountPage.this, "Password badly copy", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    protected void createAccount(View v) {

        String req =    "{" +
                "\"surname\" : \"" + editText_surname.getText().toString() + "\"," +
                "\"login\" : \"" + editText_login.getText().toString() + "\"," +
                "\"password\" : \"" + editText_password.getText().toString() + "\"," +
                "\"mail\" : \"" + editText_mail.getText().toString() + "\"," +
                "\"level\" : \"student\"" +
                "}";

        requestService.requestBuilderPut("user", req)
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(CreateAccountPage.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Log.i("CreateAccountPage", "response : " + response.code());

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

        requestService.requestBuilderGet("user/byLogin", editText_login.getText().toString())
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(CreateAccountPage.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Log.i("CreateAccountPage", "response : " + response.code());

                if (response.code() == 200) {

                    User user = mapper.readValue(response.body().string(), User.class);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            rooterService.changeActivity(new Intent(v.getContext(), MainPage.class), CreateAccountPage.this, user.idUser);
                        }
                    });
                }
            }
        });
    }
}
