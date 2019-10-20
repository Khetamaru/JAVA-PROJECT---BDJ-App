package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.app.LauncherActivity;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LocationView extends Activity {

    ObjectMapper mapper;
    ListView listView;
    ArrayAdapter<Member> arrayAdapter;
    Context context;
    User user;
    Button addLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.location);

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

                Request request = new Request.Builder()
                        .url("http://192.168.43.110:8080/member")
                        .get()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("LoginPage", "fail", e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        Member[] members = new ObjectMapper().readValue(response.body().string(), Member[].class);

                        ArrayList<Member> membersList = getListData(members);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                listView = (ListView) findViewById(R.id.LocationListView);

                                listView.setAdapter(new Location_adapter(context, membersList));
                            }
                        });
                    }
                });
            }
        });

        addLocation = findViewById(R.id.fullLocation);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), LocationPlaceView.class);
                intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                startActivity(intent);
            }
        });
    }

    private ArrayList getListData(Member[] members) {
        ArrayList<Member> results = new ArrayList<>();

        for (Member member: members) {

            results.add(member);
        }

        return results;
    }
}