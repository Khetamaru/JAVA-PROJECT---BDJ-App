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
import android.widget.EditText;
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

public class LocationPlaceView extends Activity {

    EditText place;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.location_add);

        place = findViewById(R.id.place);

        next = findViewById(R.id.fullLocation);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!place.getText().toString().equals("")) {

                    Intent intent = new Intent(v.getContext(), LocationStartView.class);
                    intent.putExtra("idUser", getIntent().getIntExtra("idUser", 0));
                    intent.putExtra("place", place.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
