package com.example.animalpedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

/**
 * Αυτή η κλάση διαχειρίζεται το activity για την αναζήτηση ζώων
 */

public class Search extends AppCompatActivity {
    private RecyclerAdapter adapter;

    private SearchView searchView;
    private List<Animal> animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search); //σύνδεση του layout με τον κώδικα

        BottomNavigationView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.nav_search);


        MyDBHandler dbHandler = new MyDBHandler(this, null); //αρχικοποίηση της βάσης
        animals = dbHandler.getAnimals(null, 2);

        RecyclerView recyclerView = findViewById(R.id.search_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RecyclerAdapter(animals,1);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        searchView = findViewById(R.id.searchBar);
        searchView.setQueryHint("Search an animal");

        //παίρνουμε τη συμβολοσειρά που πληκτρολογεί ο χρήστης
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText); //παίρνουμε τα στοιχεία που θα εμφανίσει η λίστα ανάλογα με το input του χρήστη
                return false;
            }
        });

        //όταν ο χρήστης κάνει κλικ σε οποιοδήποτε σημείο του searchView θα ανοιξεί το πληκτρολογίο για το input του
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });


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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

}