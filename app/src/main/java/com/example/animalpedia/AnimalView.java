package com.example.animalpedia;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zolad.zoominimageview.ZoomInImageView;

/**
 * Αυτή η κλάση διαχειρίζεται το activity προβολής της καρτέλας ενός ζώου
 */
public class AnimalView extends AppCompatActivity {

    private MyDBHandler dbHandler; //αντικείμενο της βάσης
    private Animal animal; //αντικείμενο ζώου
    private ImageView love; //αντικείμενο του κουμπιού για την προσθήκη στα αγαπημένα

    private Toast toast; //μήνυμα ενημέρωσης χρήστη

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_view); //σύνδεση του layout με τον κώδικα

        //σύνδεση των αντικειμένων από το layout
        TextView anName;
        ZoomInImageView anImage;
        TextView anClass;
        TextView anContinent;
        ImageView youtube;
        TextView anDetails;

        anName = findViewById(R.id.an_name);
        anImage = findViewById(R.id.an_image);
        anClass = findViewById(R.id.an_class);
        anContinent = findViewById(R.id.an_continent);
        youtube = findViewById(R.id.youtube);
        love = findViewById(R.id.love);
        anDetails = findViewById(R.id.an_details);

        animal = getIntent().getParcelableExtra("AnimalId"); //σύνδεση του αντικειμένου που λαμβάνουμε από το προηγούμενο activity

        //τοποθέτηση των δεδομένων στα αντικείμενα
        anName.setText(animal.getName());
        byte[] img = animal.getImage();
        Bitmap image = BitmapFactory.decodeByteArray(img, 0, img.length);
        anImage.setImageBitmap(image);
        anClass.setText(animal.getAnimalClass());
        anContinent.setText(animal.getContinent());
        anDetails.setText(animal.getDetails());
        anDetails.setMovementMethod(new ScrollingMovementMethod());
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(animal.getLink());
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });


        dbHandler = new MyDBHandler(this, null); //αρχικοποίηση της βάσης

        //έλεγχος αν το ζώο έχει τοποθετηθεί στα αγαπημένα
        if (animal.getFavorite()){
            love.setColorFilter(Color.RED);
        }
        else {
            love.setColorFilter(getResources().getColor(android.R.color.darker_gray));
        }

        //αν γίνει κλικ στην καρδία έλεγχος της καταστασής της και εμφάνιση του αντίστοιχου μηνύματος στο χρήστη
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFav = animal.getFavorite();
                animal.setFavorite(!isFav);
                dbHandler.changeFavoriteState(animal); //αλλαγή της κατάστασης του κουμπιού love στη βάση
                if (isFav){
                    love.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                    if(toast != null){
                        toast.cancel();
                    }
                    toast = Toast.makeText(getApplicationContext(),"Animal removed from your favorites",Toast.LENGTH_SHORT);
                    toast.show();

                }
                else {
                    love.setColorFilter(Color.RED);
                    if(toast != null) {
                        toast.cancel();
                    }
                    toast = Toast.makeText(getApplicationContext(),"Animal added to your favorites!",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }


}