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

public class BorrowViewAll extends Activity {

    ObjectMapper mapper;
    ListView listView;
    ArrayAdapter<Borrow> arrayAdapter;
    Context context;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

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
                        .url("http://192.168.43.110:8080/borrow")
                        .get()
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        Log.e("InventoryView", "fail", e);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        Borrow[] borrows = new ObjectMapper().readValue(response.body().string(), Borrow[].class);

                        ArrayList<Borrow> borrowsList = getListData(borrows);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                setContentView(R.layout.borrow_all);

                                listView = (ListView) findViewById(R.id.BorrowListView);

                                listView.setAdapter(new Borrow_adapter(context, borrowsList));

                                /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                        Game game = (Game) listView.getItemAtPosition(position);
                                        Intent intent = new Intent(v.getContext(), stuffView.class);
                                        intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                        intent.putExtra("idGame", game.idGame);
                                        startActivity(intent);
                                    }
                                });*/
                            }
                        });
                    }
                });
            }
        });
    }

    private ArrayList getListData(Borrow[] borrows) {
        ArrayList<Borrow> results = new ArrayList<>();

        for (Borrow borrow: borrows) {

            results.add(borrow);
        }

        return results;
    }
}
