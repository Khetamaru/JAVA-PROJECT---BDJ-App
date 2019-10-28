package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.os.Bundle;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserHistoricView extends Activity {

    ObjectMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapper = new ObjectMapper();

        setContentView(R.layout.historic);
    }
}