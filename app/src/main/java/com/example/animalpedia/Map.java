package com.example.animalpedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Αυτή η κλάση διαχειρίζεται το activity με τον χάρτη για την εύρεση ζώων ανά ήπειρο
 */

public class Map extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map); //σύνδεση του layout με τον κώδικα

        Button southAmerica, northAmerica, europe, africa, asia, oceania, antarctica;

        southAmerica = findViewById(R.id.button_south_america);
        northAmerica = findViewById(R.id.button_north_america);
        europe = findViewById(R.id.button_europe);
        africa = findViewById(R.id.button_africa);
        asia = findViewById(R.id.button_asia);
        oceania = findViewById(R.id.button_oceania);
        antarctica = findViewById(R.id.button_antarctica);

        southAmerica.setOnClickListener(this);
        northAmerica.setOnClickListener(this);
        europe.setOnClickListener(this);
        africa.setOnClickListener(this);
        asia.setOnClickListener(this);
        oceania.setOnClickListener(this);
        antarctica.setOnClickListener(this);

        BottomNavigationView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.nav_map);

        //ελεγχος δραστηριότητας στο navigation bar
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_search:
                        intent = new Intent(getApplicationContext(),Search.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_favorites:
                        intent = new Intent(getApplicationContext(),Favorites.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_map:
                        return true;
                }
                return false;
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    /**
     * Όταν πατηθεί ένα διάφανο κουμπί μιας ηπείρου απο το χάρτη ξεκινάει νέο activity
     * @param v το αντίστοιχο κουμπί που πατάει ο χρήστης
     */
    @Override
    public void onClick(View v) {
        String buttonText = ((Button) v).getText().toString();
        Intent intent = new Intent(getBaseContext(), AnimalRecyclerView.class);
        intent.putExtra("key",buttonText);
        intent.putExtra("type", "Continent");
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}