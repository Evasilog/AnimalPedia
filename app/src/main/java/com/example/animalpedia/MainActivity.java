package com.example.animalpedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button buttonAmphibians,buttonReptiles,buttonFish,buttonBirds,buttonMammals;
    public MyDBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.Theme_AnimalPedia);
        setContentView(R.layout.activity_main);

        dbHandler = new MyDBHandler(this, null);//αρχικοποίηση της βάσης

        BottomNavigationView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.nav_home);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        return true;
                    case R.id.nav_search:
                        startActivity(new Intent(getApplicationContext(),Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_favorites:
                        startActivity(new Intent(getApplicationContext(),Favorites.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_map:
                        startActivity(new Intent(getApplicationContext(),Map.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        buttonAmphibians = findViewById(R.id.button_amphibians);
        buttonReptiles = findViewById(R.id.button_reptiles);
        buttonFish = findViewById(R.id.button_fish);
        buttonBirds = findViewById(R.id.button_birds);
        buttonMammals = findViewById(R.id.button_mammals);

        buttonAmphibians.setOnClickListener(this);
        buttonReptiles.setOnClickListener(this);
        buttonFish.setOnClickListener(this);
        buttonBirds.setOnClickListener(this);
        buttonMammals.setOnClickListener(this);

        ImageButton iB = findViewById(R.id.settings);
        iB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Settings.class));
            }
        });


    }


    /**
     * Όταν πατηθεί ένα κουμπί απο την κεντρική οθόνη με τις κατηγορίες των ζώων
     * ξεκινάει νέο activity
     * @param v το αντίστοιχο κουμπί που πατάει ο χρήστης
     */
    @Override
    public void onClick(View v) {

        String buttonText = ((Button) v).getText().toString();
        Intent intent = new Intent(getBaseContext(),CategoryView.class);
        intent.putExtra("key",buttonText);
        startActivity(intent);
    }
}