package com.example.animalpedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class ContinentView extends AppCompatActivity {

    TextView title_page;
    String titleText;

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public RecyclerView.Adapter adapter;

    public MyDBHandler dbHandler;
    private List<Animal> animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continent_view);

        Bundle extra = getIntent().getExtras();
        if (extra != null){
            titleText = extra.getString("key");
        }

        dbHandler = new MyDBHandler(this, null);
        animals = dbHandler.getAnimalCategory(titleText, 0);

        recyclerView = findViewById(R.id.recycler_view);

        //Set the layout of the items in the RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        initializeAnimalRecyclerAdapter();

        title_page = findViewById(R.id.page_title);
        title_page.setText(titleText);

        BottomNavigationView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.nav_map);

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_home:
                        finishAffinity();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_search:
                        finishAffinity();
                        startActivity(new Intent(getApplicationContext(),Search.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_favorites:
                        finishAffinity();
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

    }

    // Initializing Array adapter for the beer list
    public void initializeAnimalRecyclerAdapter()
    {
        adapter = new RecyclerAdapter(animals, 1);
        recyclerView.setAdapter(adapter);
    }
}