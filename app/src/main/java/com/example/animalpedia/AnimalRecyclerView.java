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


/**
 * Αυτή η κλάση διαχειρίζεται το activity που εμφανίζεται επιλέγοντας μια κατηγορία από το Home ή επιλέγοντας
 * μια ήπειρο από το Map
 */

public class AnimalRecyclerView extends AppCompatActivity {

    private String titleText; //το όνομα της ηπείρου ή της κατηγορίας του ζώου
    private String type; //μεταβλητή για την διάκριση μεταξύ ηπείρου και κατηγορίας

    private List<Animal> animals; //λίστα που περιέχει τα ζώα για εμφάνιση

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_recycler_view); //σύνδεση του layout με τον κώδικα


        TextView title_page; //περίεχει το όνομα της κατηγορίας ή της ηπείρου των ζώων
        RecyclerView.LayoutManager layoutManager; //ορίζει την διάταξη των αντικειμένων της λίστας
        MyDBHandler dbHandler = new MyDBHandler(this, null); //αρχικοποίηση της βάσης
        RecyclerView recyclerView; //UI στοιχείο για την εμφάνιση των ζώων
        RecyclerView.Adapter adapter; //αντικείμενο που συνδέει τα δεδομένα κάθε κάρτας ενός ζώου με τη διάταξη της κάρτας του



        //λήψη των δεδομένων από το προηγούμενο activity
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            titleText = extra.getString("key");
            type = extra.getString("type");
        }

        recyclerView = findViewById(R.id.recycler_view);

        //ορισμός του layout των αντικειμένων στο RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        title_page = findViewById(R.id.page_title);
        title_page.setText(titleText);
        View view = findViewById(R.id.globe);

        BottomNavigationView nav = findViewById(R.id.navigation_bar);

        //έλεγχος για την κατάλληλη εμφάνιση του τίτλου
        //και επιλογή του αντίστοιχου αντικειμένουν από το μενού
        if(!type.equals("Continent")) {
            ((ViewGroup) view.getParent()).removeView(view);
            animals = dbHandler.getAnimals(titleText, 1);
            nav.setSelectedItemId(R.id.nav_home);
        }else{
            animals = dbHandler.getAnimals(titleText, 0);
            nav.setSelectedItemId(R.id.nav_map);
        }

        //αρχικοποίηση των στοιχείων στο adapter
        adapter = new RecyclerAdapter(animals, 1);
        recyclerView.setAdapter(adapter);

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