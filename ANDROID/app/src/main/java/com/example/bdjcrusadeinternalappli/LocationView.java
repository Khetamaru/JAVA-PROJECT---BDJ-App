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

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.location_view);

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/user/" + getIntent().getIntExtra("idUser",0))
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e("LoginPage", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                user = mapper.readValue(response.body().string(), User.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        String url;

                        if (user.getLevel().equals("admin") || user.getLevel().equals("bdjMember")) {

                            setContentView(R.layout.location_view_all);

                            url = "http://192.168.43.110:8080/location";


                            add = findViewById(R.id.fullLocation);
                            add.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    Intent intent = new Intent(v.getContext(), LocationAdd.class);
                                    intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                    startActivity(intent);
                                }
                            });
                        }
                        else {

                            url = "http://192.168.43.110:8080/location";
                        }

                        Request request = new Request.Builder()
                                .url(url)
                                .get()
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                                Log.e("LoginPage", "fail", e);
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
                                                Intent intent = new Intent(v.getContext(), LocationDetail.class);
                                                intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                                intent.putExtra("idLocation", location.getIdLocation());
                                                startActivity(intent);
                                            }
                                        });

                                        back = findViewById(R.id.back);
                                        back.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {

                                                Intent intent;
                                                intent = new Intent(v.getContext(), MainPage.class);
                                                intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
                                                startActivity(intent);
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