package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class CreateAccountPage extends Activity {

    Button creation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LoginPage", ":)");

        setContentView(R.layout.create_account_page);

        creation = findViewById(R.id.submit);

        creation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(creation()) {

                    Intent intent = new Intent(v.getContext(), MainPage.class);
                    startActivity(intent);
                }
            }
        });
    }

    protected boolean creation() {

        return true;
    }
}
