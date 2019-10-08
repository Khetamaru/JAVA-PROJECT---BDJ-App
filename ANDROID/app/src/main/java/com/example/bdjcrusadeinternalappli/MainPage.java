package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LoginPage", ":)");

        setContentView(R.layout.main_page);
    }
}
