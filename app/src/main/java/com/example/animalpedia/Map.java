package com.example.animalpedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Map extends AppCompatActivity implements View.OnClickListener {

    Button b1,b2,b3,b4,b5,b6,b7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        b1 = findViewById(R.id.button_south_america);
        b2 = findViewById(R.id.button_north_america);
        b3 = findViewById(R.id.button_europe);
        b4 = findViewById(R.id.button_africa);
        b5 = findViewById(R.id.button_asia);
        b6 = findViewById(R.id.button_oceania);
        b7 = findViewById(R.id.button_antarctica);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);

        BottomNavigationView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.nav_map);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_home:
                        intent = new Intent(getApplicationContext(),MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    @Override
    public void onClick(View v) {
        String buttonText = ((Button) v).getText().toString();
        Intent intent = new Intent(getBaseContext(),ContinentView.class);
        intent.putExtra("key",buttonText);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}