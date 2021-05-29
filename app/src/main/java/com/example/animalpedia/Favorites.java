package com.example.animalpedia;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class Favorites extends AppCompatActivity {
    public MyDBHandler dbHandler;
    private List<Animal> favAnimals;

    private TextView fav_hint;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        BottomNavigationView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.nav_favorites);

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

                        return true;
                    case R.id.nav_map:
                        intent = new Intent(getApplicationContext(),Map.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        dbHandler = new MyDBHandler(this, null);
        favAnimals = dbHandler.getAnimalCategory(null, 3);

        recyclerView = findViewById(R.id.fav_recycler_view);

        initializeAnimalRecyclerAdapter();

        fav_hint = findViewById(R.id.fav_count);

        int count = adapter.getItemCount();

        if (count==0){
            fav_hint.setText(getString(R.string.favorites_hint_0));
        } else if (count==1) {
            fav_hint.setText(getString(R.string.favorites_hint_1));
        } else {
            fav_hint.setText(count + " " + getString(R.string.favorites_hint_2plus));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    public void initializeAnimalRecyclerAdapter() {
        adapter = new RecyclerAdapter(favAnimals,2);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }
}