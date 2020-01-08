package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fasterxml.jackson.databind.ObjectMapper;

import kotlinClass.RequestService;
import kotlinClass.RooterService;

public class levelUpView extends Activity {

    ObjectMapper mapper;

    RequestService requestService = new RequestService();
    RooterService rooterService = new RooterService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.inventory);
        rooterService.changeActivity(new Intent(this, MainPage.class), levelUpView.this, getIntent().getIntExtra("idUser",0));
    }
}