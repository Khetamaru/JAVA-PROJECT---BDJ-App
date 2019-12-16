package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import kotlinClass.LocationAdd;
import kotlinClass.RequestService;
import kotlinClass.RooterService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LocationView extends Activity {

    ObjectMapper mapper;
    ListView listView;
    ArrayAdapter<Location> arrayAdapter;
    Context context;
    User user;
    Button add;
    Button back;

    RequestService requestService;
    RooterService rooterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.loading_page);

        mapper = new ObjectMapper();

        requestService.requestBuilderGet("user", getIntent().getIntExtra("idUser",0))
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Toast.makeText(LocationView.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                user = mapper.readValue(response.body().string(), User.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(R.layout.location_view);

                        String url;

                        if (user.getLevel().equals("admin") || user.getLevel().equals("bdjMember")) {

                            add = findViewById(R.id.fullLocation);
                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    rooterService.changeActivity(new Intent(v.getContext(), LocationAdd.class), LocationView.this, getIntent().getIntExtra("idUser",0));
                                }
                            });
                        }

                        requestService.requestBuilderGet("location")
                                .enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Toast.makeText(LocationView.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                                Location[] locations = new ObjectMapper().readValue(response.body().string(), Location[].class);

                                ArrayList<Location> locationsList = getListData(locations);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        listView = (ListView) findViewById(R.id.LocationListView);

                                        listView.setAdapter(new Location_adapter(context, locationsList));

                                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                                Location location = (Location) listView.getItemAtPosition(position);
                                                rooterService.changeActivity(new Intent(v.getContext(), LocationDetail.class), LocationView.this, getIntent().getIntExtra("idUser",0), location.getIdLocation(), "idLocation");
                                            }
                                        });

                                        back = findViewById(R.id.back);
                                        back.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                rooterService.changeActivity(new Intent(v.getContext(), MainPage.class), LocationView.this, getIntent().getIntExtra("idUser", 0));
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private ArrayList getListData(Location[] locations) {
        ArrayList<Location> results = new ArrayList<>();

        for (Location location : locations) {

            results.add(location);
        }

        return results;
    }
}