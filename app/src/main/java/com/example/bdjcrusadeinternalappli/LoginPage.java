package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class LoginPage extends Activity {

    Button co;
    Button insc;
    EditText login;
    EditText passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("LoginPage", ":)");

        setContentView(R.layout.login_page);

        co = findViewById(R.id.connect);
        insc = findViewById(R.id.create);
        login = findViewById(R.id.login);
        passw = findViewById(R.id.password);

        co.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String log = login.getText().toString();
                String pass = passw.getText().toString();

                if (verifConnect(log, pass)) {

                    Intent intent = new Intent(v.getContext(), MainPage.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginPage.this, "Connection failed -- Login : " + log + " Password : " + pass, Toast.LENGTH_LONG).show();
                    Log.i("LoginPage", "onClickLogin");
                }
            }
        });

        insc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateAccountPage.class);
                startActivity(intent);
            }
        });
    }

    protected boolean verifConnect(String login, String password) {

        if (login.equals("oui") && password.equals("non")) {

            return true;
        }
        else {

            return false;
        }
    }
}
