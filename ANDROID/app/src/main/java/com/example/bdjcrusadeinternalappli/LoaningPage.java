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

import kotlinClass.RequestService;
import kotlinClass.RooterService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoaningPage extends Activity {

    ObjectMapper mapper;
    ListView listView;
    ArrayAdapter<Loaning> arrayAdapter;
    Context context;
    User user;
    Button loaningViewAll;
    Button back;
    Button add;

    RequestService requestService = new RequestService();
    RooterService rooterService = new RooterService();

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
                Toast.makeText(LoaningPage.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                user = mapper.readValue(response.body().string(), User.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(R.layout.loaning);

                        adminCheck();

                        back = findViewById(R.id.back);
                        back.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                rooterService.changeActivity(new Intent(v.getContext(), MainPage.class), LoaningPage.this, getIntent().getIntExtra("idUser", 0));
                            }
                        });

                        add = findViewById(R.id.add);
                        add.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                rooterService.changeActivity(new Intent(v.getContext(), LoaningAddEquipment.class), LoaningPage.this, getIntent().getIntExtra("idUser", 0));
                            }
                        });
                    }
                });

                requestService.requestBuilderPost("loaning/all", user.toString())
                        .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        Toast.makeText(LoaningPage.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        Loaning[] loans = new ObjectMapper().readValue(response.body().string(), Loaning[].class);

                        ArrayList<Loaning> loansList = getListData(loans);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                listView = (ListView) findViewById(R.id.loaningListView);

                                listView.setAdapter(new Loaning_adapter(context, loansList));

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> a, View v, int position, long textView_id) {
                                        Loaning loaning = (Loaning) listView.getItemAtPosition(position);

                                        rooterService.changeActivity(new Intent(v.getContext(), LoaningDetails.class), LoaningPage.this, getIntent().getIntExtra("idUser",0), loaning.getIdLoaning(), "idLoaning", "one", "context");
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }

    private void adminCheck() {

        if (user.level.equals("admin")) {

            setContentView(R.layout.loaning_admin);

            loaningViewAll = findViewById(R.id.fullBorrow);
            loaningViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    rooterService.changeActivity(new Intent(v.getContext(), LoaningViewAll.class), LoaningPage.this, getIntent().getIntExtra("idUser", 0));
                }
            });
        }
    }

    private ArrayList getListData(Loaning[] loanings) {
        ArrayList<Loaning> results = new ArrayList<>();

        for (Loaning loaning : loanings) {

            results.add(loaning);
        }

        return results;
    }
}