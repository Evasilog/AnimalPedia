package com.example.animalpedia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * Αυτή η κλάση διαχειρίζεται το main activity με τις κατηγορίες των ζώων
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int counter = 0;
    private Toast toast;

    private BottomNavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //σύνδεση του layout με τον κώδικα

        Button buttonAmphibians,buttonReptiles,buttonFish,buttonBirds,buttonMammals;

        //έλεγχος και εφαρμογή του κατάλληλου θέματος
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPreferences.getString("listPref_theme", "false");
        if ("default".equals(theme)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        } else if ("light".equals(theme)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else if ("dark".equals(theme)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }


        nav = findViewById(R.id.navigation_bar);
        nav.setSelectedItemId(R.id.nav_home);


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

        //κουμπί ρυθμίσεων
        ImageButton iB = findViewById(R.id.settings);
        iB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Settings.class));
            }
        });

        //ελεγχος δραστηριότητας στο navigation bar
        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_home:
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
        counter++;
        if(counter == 1) {
            toast = Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT);
            toast.show();
        }
        if(counter == 2) {
            if(toast != null)
                toast.cancel();
            finishAffinity();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        nav.setSelectedItemId(R.id.nav_home);
    }

    /**
     * Όταν πατηθεί ένα κουμπί απο την κεντρική οθόνη με τις κατηγορίες των ζώων ξεκινάει νέο activity
     * @param v το αντίστοιχο κουμπί που πατάει ο χρήστης
     */
    @Override
    public void onClick(View v) {
        String buttonText = ((Button) v).getText().toString();
        Intent intent = new Intent(getBaseContext(), AnimalRecyclerView.class);
        intent.putExtra("key",buttonText); //αποστολή κειμένου του κουμπιού στο νέο activity
        intent.putExtra("type", "Category");
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}