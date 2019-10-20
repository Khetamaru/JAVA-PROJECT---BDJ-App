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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InventoryView extends Activity {

    ObjectMapper mapper;
    ListView listView;
    ArrayAdapter<Game> arrayAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.43.110:8080/game")
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                Log.e("InventoryView", "fail", e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                Game[] games = new ObjectMapper().readValue(response.body().string(), Game[].class);

                ArrayList<Game> gamesList = getListData(games);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        setContentView(R.layout.inventory);

                        listView = (ListView) findViewById(R.id.InventoryListView);

                        listView.setAdapter(new inventory_adapter(context, gamesList));

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                Game game = (Game) listView.getItemAtPosition(position);
                                Intent intent = new Intent(v.getContext(), stuffView.class);
                                intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
                                intent.putExtra("idGame", game.idGame);
                                startActivity(intent);
                            }
                        });
                    }
                });
            }
        });
    }

    private ArrayList getListData(Game[] games) {
        ArrayList<Game> results = new ArrayList<>();

        for (Game game: games) {

            results.add(game);
        }

        return results;
    }
}
