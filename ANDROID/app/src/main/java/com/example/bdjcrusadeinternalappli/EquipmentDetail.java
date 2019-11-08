package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Date;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.equipment_details);

        OkHttpClient client = new OkHttpClient();

        mapper = new ObjectMapper();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/equipment/" + getIntent().getIntExtra("idEquipment",0))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("EquipmentDetail", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                equipment = mapper.readValue(response.body().string(), Equipment.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        name = findViewById(R.id.name);
                        name.setText(equipment.getName());

                        status = findViewById(R.id.status);
                        status.setText(equipment.getStatus());

                        recupDate = findViewById(R.id.recupDate);
                        recupDate.setText(equipment.getDateRecup().toString());

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

                                Intent intent;
                                intent = new Intent(v.getContext(), EquipmentView.class);
                                intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }
}
