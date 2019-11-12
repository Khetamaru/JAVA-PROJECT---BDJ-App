package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;

public class levelUpView extends Activity {

    ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Intent intent = new Intent(this, MainPage.class);
        intent.putExtra("idUser", getIntent().getIntExtra("idUser",0));
        startActivity(intent);
    }
}