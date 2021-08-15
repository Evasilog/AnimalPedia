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
 * en
 * This class is used to manipulate the activity when the user selects an animal class from home
 * page(amphibians, reptiles etc) or from the map page where the user selects a continent.
 * This is the same activity
 * -------------------------------------
 * el
 * Αυτή η κλάση διαχειρίζεται το activity που εμφανίζεται επιλέγοντας μια κατηγορία από το Home ή επιλέγοντας
 * μια ήπειρο από το Map
 */


public class AnimalRecyclerView extends AppCompatActivity {

    //contains the name of the continent or the name of the animal class
    //το όνομα της ηπείρου ή της κατηγορίας του ζώου
    private String titleText;

    //variable for the discrimination between the continent and the animal class
    //μεταβλητή για την διάκριση μεταξύ ηπείρου και κατηγορίας
    private String type;

    //list that has the animals that about to show
    //λίστα που περιέχει τα ζώα για εμφάνιση
    private List<Animal> animals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //connect the layout with the code
        //σύνδεση του layout με τον κώδικα
        setContentView(R.layout.activity_animal_recycler_view);


        //contains the name of the continent or the name of the animal class
        //περίεχει το όνομα της κατηγορίας ή της ηπείρου των ζώων
        TextView title_page;

        //sets the layout of the list's objects
        //ορίζει την διάταξη των αντικειμένων της λίστας
        RecyclerView.LayoutManager layoutManager;

        //initialization of the database
        //αρχικοποίηση της βάσης
        MyDBHandler dbHandler = new MyDBHandler(this, null);

        //UI element for the animals that about to show
        //UI στοιχείο για την εμφάνιση των ζώων
        RecyclerView recyclerView;

        //object that links the data of each animal card with the layout of the card
        //αντικείμενο που συνδέει τα δεδομένα κάθε κάρτας ενός ζώου με τη διάταξη της κάρτας του
        RecyclerView.Adapter adapter;


        //gets the data from the previous activity
        //λήψη των δεδομένων από το προηγούμενο activity
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            titleText = extra.getString("key");
            type = extra.getString("type");
        }

        recyclerView = findViewById(R.id.recycler_view);

        //sets the layout of the object on the RecyclerView
        //ορισμός του layout των αντικειμένων στο RecyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        title_page = findViewById(R.id.page_title);
        title_page.setText(titleText);
        View view = findViewById(R.id.globe);

        BottomNavigationView nav = findViewById(R.id.navigation_bar);

        //checks the correct appearance of the title and selects of the corresponding object from menu
        //έλεγχος για την κατάλληλη εμφάνιση του τίτλου και επιλογή του αντίστοιχου αντικειμένουν από το μενού
        if(!type.equals("Continent")) {
            ((ViewGroup) view.getParent()).removeView(view);
            animals = dbHandler.getAnimals(titleText, 1);
            nav.setSelectedItemId(R.id.nav_home);
        }else{
            animals = dbHandler.getAnimals(titleText, 0);
            nav.setSelectedItemId(R.id.nav_map);
        }

        //initialization of the elements on the adapter
        //αρχικοποίηση των στοιχείων στο adapter
        adapter = new RecyclerAdapter(animals, 1);
        recyclerView.setAdapter(adapter);

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