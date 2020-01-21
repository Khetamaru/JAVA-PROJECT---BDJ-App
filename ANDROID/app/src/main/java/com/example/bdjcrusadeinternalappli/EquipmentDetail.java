package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import kotlinClass.RequestService;
import kotlinClass.RooterService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class EquipmentDetail extends Activity {

    User user;
    Equipment equipment;
    ObjectMapper mapper;

    TextView name;
    TextView status;
    TextView recupDate;
    TextView state;
    TextView origin;
    TextView cfDoc;
    TextView ableToBorrow;

    Button back;

    RequestService requestService = new RequestService();
    RooterService rooterService = new RooterService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loading_page);

        requestService.requestBuilderGet("equipment", getIntent().getIntExtra("idEquipment",0))
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(EquipmentDetail.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                equipment = mapper.readValue(response.body().string(), Equipment.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(R.layout.equipment_details);

                        name = findViewById(R.id.name);
                        name.setText(equipment.getName());

                        status = findViewById(R.id.status);
                        status.setText(equipment.getStatus());

                        recupDate = findViewById(R.id.recupDate);
                        String[] date = equipment.getDateRecup().toString().split(" ");
                        recupDate.setText(date[2] + " " + date[1] + " " + date[5]);

                        state = findViewById(R.id.state);
                        state.setText(equipment.getState());

                        origin = findViewById(R.id.origin);
                        origin.setText(equipment.getOrigin());

                        cfDoc = findViewById(R.id.cfDoc);
                        cfDoc.setText(equipment.getCfDoc());

                        ableToBorrow = findViewById(R.id.ableToBorrow);
                        ableToBorrow.setText(equipment.getAbleToBorrow());

                        back = findViewById(R.id.back);
                        back.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                rooterService.changeActivity(new Intent(v.getContext(), EquipmentView.class), EquipmentDetail.this, getIntent().getIntExtra("idUser", 0));
                            }
                        });
                    }
                });
            }
        });
    }
}
