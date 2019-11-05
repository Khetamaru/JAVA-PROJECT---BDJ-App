package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

public class LoaningViewAll extends Activity {

    ObjectMapper mapper;
    ListView listView;
    ArrayAdapter<Loaning> arrayAdapter;
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

                        Loaning[] loanings = new ObjectMapper().readValue(response.body().string(), Loaning[].class);

                        ArrayList<Loaning> borrowsList = getListData(loanings);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                setContentView(R.layout.loaning_all);

                                listView = (ListView) findViewById(R.id.BorrowListView);

                                listView.setAdapter(new Loaning_adapter(context, borrowsList));

                                /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> a, View v, int position, long textView_id) {
                                        Equipment equipment = (Equipment) listView.getItemAtPosition(position);
                                        Intent intent = new Intent(v.getContext(), EquipmentDetail.class);
                                        intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                        intent.putExtra("idGame", equipment.idGame);
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

    private ArrayList getListData(Loaning[] loanings) {
        ArrayList<Loaning> results = new ArrayList<>();

        for (Loaning loaning : loanings) {

            results.add(loaning);
        }

        return results;
    }
}
