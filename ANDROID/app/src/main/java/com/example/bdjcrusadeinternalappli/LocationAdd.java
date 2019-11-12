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
import android.widget.TimePicker;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.sql.Time;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LocationAdd extends Activity {

    User user;
    Equipment equipment;
    Date date;
    Time startTime;
    Time endTime;
    String place;

    EditText dateText;
    EditText placeText;
    TimePicker startTimeText;
    TimePicker endTimeText;
    Context context;
    ObjectMapper mapper;

    Button validation;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.location_add);

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user/" + getIntent().getIntExtra("idUser", 0))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("LocationAdd", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                user = mapper.readValue(response.body().string(), User.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        placeText = findViewById(R.id.placeText);
                        startTimeText = findViewById(R.id.startTime);
                        endTimeText = findViewById(R.id.endTime);

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

                                if (!dateText.getText().equals("")) {

                                    mapper = new ObjectMapper();

                                    OkHttpClient client = new OkHttpClient();

                                    place = placeText.getText().toString();
                                    date = new Date(dateText.getText().toString());
                                    startTime = new Time(startTimeText.getHour() + startTimeText.getMinute());
                                    endTime = new Time(endTimeText.getHour() + endTimeText.getMinute());


                                    Location location = new Location(user, place, date, startTime, endTime);

                                    String rq = location.toStringWithoutId();

                                    MediaType JSON = MediaType.get("application/json; charset=utf-8");

                                    RequestBody body = RequestBody.create(JSON, rq);

                                    Request request = new Request.Builder()
                                            .url("http://192.168.43.110:8080/location")
                                            .put(body)
                                            .build();

                                    client.newCall(request).enqueue(new Callback() {
                                        @Override
                                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                            Log.e("LocationAdd", "fail", e);
                                        }

                                        @Override
                                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                            Intent intent;
                                            intent = new Intent(v.getContext(), LocationView.class);
                                            intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
                                            startActivity(intent);
                                        }
                                    });
                                }
                                else {

                                    Toast.makeText(LocationAdd.this, "One or all field(s) is empty", Toast.LENGTH_LONG).show();
                                    Log.i("LocationAdd", "One or all field(s) is empty");
                                }
                            }
                        });

                        dateText = findViewById(R.id.dateText);

                        dateText.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                            public void onFocusChange(View view, boolean hasFocus) {

                                if (hasFocus) {

                                    DateDialog dialog = new DateDialog(view);
                                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                    dialog.show(fragmentTransaction, "DatePicker");
                                }
                            }
                        });
                    }
                });
            }
        });
    }
}