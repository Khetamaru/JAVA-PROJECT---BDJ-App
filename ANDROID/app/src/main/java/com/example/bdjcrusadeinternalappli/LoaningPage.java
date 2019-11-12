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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        setContentView(R.layout.loaning);

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

                        adminCheck();

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

                        add = findViewById(R.id.add);
                        add.setOnClickListener(new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                                Intent intent;
                                intent = new Intent(v.getContext(), LoaningAddEquipment.class);
                                intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
                                startActivity(intent);
                            }
                        });
                    }
                });

                MediaType JSON
                        = MediaType.get("application/json; charset=utf-8");

                RequestBody body = RequestBody.create(JSON, user.toString());

                Request request = new Request.Builder()
                        .url("http://192.168.43.110:8080/loaning/all")
                        .post(body)
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        Log.e("EquipmentView", "fail", e);
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
                                        Intent intent = new Intent(v.getContext(), LoaningDetails.class);
                                        intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                        intent.putExtra("idLoaning", loaning.getIdLoaning());
                                        intent.putExtra("context", "one");
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

    private void adminCheck() {

        if (user.level.equals("admin")) {

            setContentView(R.layout.loaning_admin);

            loaningViewAll = findViewById(R.id.fullBorrow);
            loaningViewAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), LoaningViewAll.class);
                    intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
                    startActivity(intent);
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