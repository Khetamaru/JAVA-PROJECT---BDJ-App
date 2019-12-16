package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;

import kotlinClass.RooterService;

public class levelUpView extends Activity {

    ObjectMapper mapper;
    RooterService rooterService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.inventory);
        rooterService.changeActivity(new Intent(this, MainPage.class), levelUpView.this, getIntent().getIntExtra("idUser",0));
    }
}