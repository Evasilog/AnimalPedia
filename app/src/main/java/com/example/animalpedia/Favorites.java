package com.example.animalpedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/**
 * en
 * This class manipulates the activity with the favorite animals of the user
 * -------------------------------------
 * el
 * Αυτή η κλάση διαχειρίζεται το activity με τα αγαπημένα ζώα του χρήστη
 */

public class Favorites extends AppCompatActivity {

    //list with the favorite animals
    //λίστα με τα αγαπημένα ζώα
    private List<Animal> favAnimals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //connect the layout with the code
        //σύνδεση του layout με τον κώδικα
        setContentView(R.layout.activity_favorites);

        BottomNavigationView nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.nav_favorites);

        //message that holds the number of the animals that are on the favorites
        //μήνυμα για τον αριθμό των ζώων στα αγαπημένα
        TextView fav_hint = findViewById(R.id.fav_count);

        //database initialization
        //αρχικοποίηση της βάσης
        MyDBHandler dbHandler = new MyDBHandler(this, null);

        //store the animals into list
        //αποθήκευση των ζώων στη λίστα
        favAnimals = dbHandler.getAnimals(null, 3);

        RecyclerView recyclerView = findViewById(R.id.fav_recycler_view);

        RecyclerView.Adapter adapter = new RecyclerAdapter(favAnimals,2);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        //sets the right message on the top of the activity about how many animals are stored
        //εμφάνιση του κατάλληλου μηνύματος
        int count = adapter.getItemCount();
        if (count==0){
            fav_hint.setText(getString(R.string.favorites_hint_0));
        } else if (count==1) {
            fav_hint.setText(getString(R.string.favorites_hint_1));
        } else {
            fav_hint.setText(count + " " + getString(R.string.favorites_hint_2plus));
        }

        //checks what the user selects from navigation bar
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


    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0,0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
    }
}