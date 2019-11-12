package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class UserHistoricView extends Activity {

    ObjectMapper mapper;
    Context context;

    ListView listView;

    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapper = new ObjectMapper();

        setContentView(R.layout.historic_view);

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/historic")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Log.e("UserHistoricView", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                UserHistoric[] userHistories = new ObjectMapper().readValue(response.body().string(), UserHistoric[].class);

                ArrayList<UserHistoric> userHistoriesList = getListData(userHistories);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        listView = (ListView) findViewById(R.id.userHistoricListView);

                        context = UserHistoricView.this;

                        listView.setAdapter(new UserHistoric_adapter(context, userHistoriesList));

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                UserHistoric userHistoric = (UserHistoric) listView.getItemAtPosition(position);
                                Intent intent = new Intent(v.getContext(), UserHistoricDetail.class);
                                intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                intent.putExtra("idUserHistoric", userHistoric.getIdUserHistoric());
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

    private ArrayList getListData(UserHistoric[] userHistories) {

        ArrayList<UserHistoric> results = new ArrayList<>();

        for (UserHistoric userHistoric : userHistories) {

            results.add(userHistoric);
        }

        return results;
    }
}