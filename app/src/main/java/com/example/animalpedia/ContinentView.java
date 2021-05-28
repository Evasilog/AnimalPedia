package com.example.animalpedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class ContinentView extends AppCompatActivity {

    private TextView title_page;
    private String titleText;
    private String type;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private MyDBHandler dbHandler;
    private List<Animal> animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_view);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            titleText = extra.getString("key");
            type = extra.getString("type");
        }

        dbHandler = new MyDBHandler(this, null);


        recyclerView = findViewById(R.id.recycler_view);

        //Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        title_page = findViewById(R.id.page_title);
        title_page.setText(titleText);
        View view = findViewById(R.id.globe);

        BottomNavigationView nav = findViewById(R.id.navigation_bar);

        if(!type.equals("Continent")) {
            ((ViewGroup) view.getParent()).removeView(view);
            animals = dbHandler.getAnimalCategory(titleText, 1);
            nav.setSelectedItemId(R.id.nav_home);
        }else{
            animals = dbHandler.getAnimalCategory(titleText, 0);
            nav.setSelectedItemId(R.id.nav_map);
        }

        initializeAnimalRecyclerAdapter();

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
                        intent = new Intent(getApplicationContext(),Map.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

    }

    // Initializing Array adapter for the beer list
    public void initializeAnimalRecyclerAdapter() {
        adapter = new RecyclerAdapter(animals, 1);
        recyclerView.setAdapter(adapter);
    }
}