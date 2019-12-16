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
import java.util.Date;

import kotlinClass.RequestService;
import kotlinClass.RooterService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoaningDetails extends Activity {

    TextView userName;
    TextView equipmentName;
    TextView startDate;
    TextView endDate;

    Button back;
    Button delete;

    ObjectMapper mapper;

    Loaning loaning;
    User user;

    RequestService requestService;
    RooterService rooterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LoaningDetails", "creation");

        setContentView(R.layout.loading_page);

        mapper = new ObjectMapper();

        requestService.requestBuilderGet("loaning", getIntent().getIntExtra("idLoaning",0))
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(LoaningDetails.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                loaning = new ObjectMapper().readValue(response.body().string(), Loaning.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(R.layout.loaning_details);

                        userName = findViewById(R.id.textUserName);
                        userName.setText(loaning.getUser().getSurname());

                        equipmentName = findViewById(R.id.textEquipmentName);
                        equipmentName.setText(loaning.getEquipment().getName());

                        startDate = findViewById(R.id.startDate);
                        startDate.setText(loaning.getStartDate().toString());

                        endDate = findViewById(R.id.endDate);
                        endDate.setText(loaning.getEndDate().toString());

                        back = findViewById(R.id.back);
                        back.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                Intent intent;

                                if (getIntent().getStringExtra("context").equals("one")) {

                                    intent = new Intent(v.getContext(), LoaningPage.class);
                                }
                                else {

                                    intent = new Intent(v.getContext(), LoaningViewAll.class);
                                }

                                rooterService.changeActivity(intent, LoaningDetails.this, getIntent().getIntExtra("idUser", 0));
                            }
                        });

                        delete = findViewById(R.id.delete);
                        delete.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                mapper = new ObjectMapper();

                                requestService.requestBuilderDelete("loaning", getIntent().getIntExtra("idLoaning", 0))
                                        .enqueue(new Callback() {
                                    @Override
                                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                                        Toast.makeText(LoaningDetails.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                        if(response.code() == 200) {

                                            Intent intent;

                                            if (getIntent().getStringExtra("context").equals("one")) {

                                                intent = new Intent(v.getContext(), LoaningPage.class);
                                            }
                                            else {

                                                intent = new Intent(v.getContext(), LoaningViewAll.class);
                                            }

                                            rooterService.changeActivity(intent, LoaningDetails.this, getIntent().getIntExtra("idUser", 0));
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
