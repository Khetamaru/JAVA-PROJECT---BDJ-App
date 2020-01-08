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
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import kotlinClass.RequestService;
import kotlinClass.RooterService;
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

    RequestService requestService = new RequestService();
    RooterService rooterService = new RooterService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapper = new ObjectMapper();

        setContentView(R.layout.loading_page);

        mapper = new ObjectMapper();

        requestService.requestBuilderGet("historic")
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Toast.makeText(UserHistoricView.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                UserHistoric[] userHistories = new ObjectMapper().readValue(response.body().string(), UserHistoric[].class);

                ArrayList<UserHistoric> userHistoriesList = getListData(userHistories);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(R.layout.historic_view);

                        listView = (ListView) findViewById(R.id.userHistoricListView);

                        context = UserHistoricView.this;

                        listView.setAdapter(new UserHistoric_adapter(context, userHistoriesList));

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                                UserHistoric userHistoric = (UserHistoric) listView.getItemAtPosition(position);
                                rooterService.changeActivity(new Intent(v.getContext(), UserHistoricDetail.class), UserHistoricView.this, getIntent().getIntExtra("idUser",0), userHistoric.getIdUserHistoric(), "idUserHistoric");
                            }
                        });

                        back = findViewById(R.id.back);
                        back.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                rooterService.changeActivity(new Intent(v.getContext(), MainPage.class), UserHistoricView.this, getIntent().getIntExtra("idUser", 0));
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