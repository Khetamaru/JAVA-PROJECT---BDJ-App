package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoaningAddNext extends Activity {

    User user;
    Equipment equipment;
    Date startDate;
    Date endDate;

    EditText startDateText;
    EditText endDateText;
    Context context;
    ObjectMapper mapper;

    Button validation;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.loaning_add_next);

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user/" + getIntent().getIntExtra("idUser", 0))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("LoaningAddNext", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                user = mapper.readValue(response.body().string(), User.class);

                mapper = new ObjectMapper();

                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("http://192.168.43.110:8080/equipment/" + getIntent().getIntExtra("idEquipment", 0))
                        .get()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("LoaningAddNext", "fail", e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        equipment = mapper.readValue(response.body().string(), Equipment.class);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                startDateText = findViewById(R.id.startDateText);

                                startDateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                                    public void onFocusChange(View view, boolean hasFocus) {

                                        if (hasFocus) {

                                            DateDialog dialog = new DateDialog(view);
                                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                            dialog.show(fragmentTransaction, "DatePicker");
                                        }
                                    }
                                });

                                endDateText = findViewById(R.id.endDateText);

                                endDateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                                    public void onFocusChange(View view, boolean hasFocus) {

                                        if (hasFocus) {

                                            DateDialog dialog = new DateDialog(view);
                                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                            dialog.show(fragmentTransaction, "DatePicker");
                                        }
                                    }
                                });

                                back = findViewById(R.id.back);
                                back.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        Intent intent;
                                        intent = new Intent(v.getContext(), LoaningAddEquipment.class);
                                        intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
                                        startActivity(intent);
                                    }
                                });

                                validation = findViewById(R.id.validation);
                                validation.setOnClickListener(new View.OnClickListener() {

                                    @Override
                                    public void onClick(View v) {

                                        if (    !startDateText.getText().equals("") &
                                                !endDateText.getText().equals("")) {

                                            mapper = new ObjectMapper();

                                            OkHttpClient client = new OkHttpClient();

                                            startDate = new Date(startDateText.getText().toString());
                                            endDate = new Date(endDateText.getText().toString());

                                            Loaning loaning = new Loaning(user, equipment, startDate, endDate, "no");

                                            String rq = loaning.toStringWithoutId();

                                            MediaType JSON = MediaType.get("application/json; charset=utf-8");

                                            RequestBody body = RequestBody.create(JSON, rq);

                                            Request request = new Request.Builder()
                                                    .url("http://192.168.43.110:8080/loaning")
                                                    .put(body)
                                                    .build();

                                            client.newCall(request).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                                    Log.e("LoaningAddNext", "fail", e);
                                                }

                                                @Override
                                                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                                    Intent intent;
                                                    intent = new Intent(v.getContext(), LoaningPage.class);
                                                    intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
                                                    startActivity(intent);
                                                }
                                            });
                                        }
                                        else {

                                            Toast.makeText(LoaningAddNext.this, "One or all date(s) isn't picked", Toast.LENGTH_LONG).show();
                                            Log.i("LoaningAddNext", "One or all date(s) isn't picked");
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}
