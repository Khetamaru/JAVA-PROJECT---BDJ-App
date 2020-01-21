package com.example.bdjcrusadeinternalappli;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import kotlinClass.AdminMenu;
import kotlinClass.EquipmentSelectTypeMenu;
import kotlinClass.EventMenu;
import kotlinClass.PersonnalPage;
import kotlinClass.RequestService;
import kotlinClass.RooterService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainPage extends Activity {

    TextView textView_surname;
    TextView textView_level;

    LinearLayout nameSpace;

    Button button_logOut;

    Button button_loaning;
    Button button_location;
    Button button_inventory;
    Button button_userHistoric;
    Button button_adminMenu;
    Button button_event;

    Button button_levelUp;

    ObjectMapper mapper;

    User user;

    RequestService requestService = new RequestService();
    RooterService rooterService = new RooterService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainPage", "creation");

        setContentView(R.layout.main_page);

        mapper = new ObjectMapper();

        OkHttpClient client = new OkHttpClient();

        requestService.requestBuilderGet("user", getIntent().getIntExtra("idUser",0))
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(MainPage.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                user = mapper.readValue(response.body().string(), User.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        levelChoice();
                        textView_surname = findViewById(R.id.surname);
                        textView_surname.setText(user.surname);

                        nameSpace = findViewById(R.id.name);
                        nameSpace.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                rooterService.changeActivity(new Intent(v.getContext(), PersonnalPage.class), MainPage.this, user.idUser);
                            }
                        });

                        textView_level = findViewById(R.id.level);
                        textView_level.setText(user.level);

                        button_logOut = findViewById(R.id.disconnection);
                        button_logOut.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                logOut(v);
                            }
                        });
                    }
                });
            }
        });
    }

    protected void levelChoice() {

        switch (user.level) {

            case("student"):

            case("cotisant"):

                setContentView(R.layout.main_page_student);

                button_loaning = findViewById(R.id.loaning);
                button_loaning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLoaning(v);
                    }
                });

                button_location = findViewById(R.id.location);
                button_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLocation(v);
                    }
                });

                button_levelUp = findViewById(R.id.levelUp);
                button_levelUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLevelUp(v);
                    }
                });
                break;

            case("bdjMember"):

                setContentView(R.layout.main_page_bdj_member);
                button_inventory = findViewById(R.id.inventory);
                button_inventory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToInventory(v);
                    }
                });

                button_loaning = findViewById(R.id.loaning);
                button_loaning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLoaning(v);
                    }
                });

                button_location = findViewById(R.id.location);
                button_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLocation(v);
                    }
                });

                button_levelUp = findViewById(R.id.levelUp);
                button_levelUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLevelUp(v);
                    }
                });
                break;

            case("admin"):

                setContentView(R.layout.main_page_admin);

                button_inventory = findViewById(R.id.inventory);
                button_inventory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToInventory(v);
                    }
                });

                button_loaning = findViewById(R.id.loaning);
                button_loaning.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLoaning(v);
                    }
                });

                button_location = findViewById(R.id.location);
                button_location.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToLocation(v);
                    }
                });

                button_userHistoric = findViewById(R.id.historic);
                button_userHistoric.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToHistoric(v);
                    }
                });

                button_adminMenu = findViewById(R.id.adminMenu);
                button_adminMenu.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToAdminMenu(v);
                    }
                });

                button_event = findViewById(R.id.event);
                button_event.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        goToEvent(v);
                    }
                });
                break;
        }
    }

    protected void goToInventory(View v) {

        rooterService.changeActivity(new Intent(v.getContext(), EquipmentSelectTypeMenu.class), MainPage.this, user.idUser);
    }

    protected void goToLoaning(View v) {

        rooterService.changeActivity(new Intent(v.getContext(), LoaningPage.class), MainPage.this, user.idUser);
    }

    protected void goToLocation(View v) {

        rooterService.changeActivity(new Intent(v.getContext(), LocationView.class), MainPage.this, user.idUser);
    }

    protected void goToHistoric(View v) {

        rooterService.changeActivity(new Intent(v.getContext(), UserHistoricView.class), MainPage.this, user.idUser);
    }

    protected void goToAdminMenu(View v) {

        rooterService.changeActivity(new Intent(v.getContext(), AdminMenu.class), MainPage.this, user.idUser);
    }

    protected void goToEvent(View v) {

        rooterService.changeActivity(new Intent(v.getContext(), EventMenu.class), MainPage.this, user.idUser);
    }

    protected void goToLevelUp(View v) {

        OkHttpClient client = new OkHttpClient();
        String stringBody = "{}";

        requestService.requestBuilderPatch("user/levelUp", user.idUser, "{}")
                .enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(MainPage.this, "Conversation with server fail", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                rooterService.changeActivity(new Intent(v.getContext(), levelUpView.class), MainPage.this, user.idUser);
            }
        });
    }

    protected void logOut(View v) {

        rooterService.changeActivity(new Intent(v.getContext(), LoginPage.class), MainPage.this);
        finish();
    }
}
