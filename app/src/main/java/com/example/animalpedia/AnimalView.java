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
 * en
 * This class manipulates the activity that shows the detailed card of an animal (image, details, animal class etc)
 * -------------------------------------
 * el
 * Αυτή η κλάση διαχειρίζεται το activity προβολής της καρτέλας ενός ζώου
 */
public class AnimalView extends AppCompatActivity {

    //database object
    //αντικείμενο της βάσης
    private MyDBHandler dbHandler;

    //animal object
    //αντικείμενο ζώου
    private Animal animal;

    //an image button object that adds an animal on favorites
    //αντικείμενο του κουμπιού για την προσθήκη στα αγαπημένα
    private ImageView love;

    //object that informs the user with a message
    //μήνυμα ενημέρωσης χρήστη
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //connect the layout with the code
        //σύνδεση του layout με τον κώδικα
        setContentView(R.layout.activity_animal_view);

        //links the objects from layout
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

        //links the object that we get from the previous activity
        //σύνδεση του αντικειμένου που λαμβάνουμε από το προηγούμενο activity
        animal = getIntent().getParcelableExtra("AnimalId");

        //sets the data into the objects
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

        //database initialization
        //αρχικοποίηση της βάσης
        dbHandler = new MyDBHandler(this, null);

        //checks if the animal is already set as favorite
        //έλεγχος αν το ζώο έχει τοποθετηθεί στα αγαπημένα
        if (animal.getFavorite()){
            love.setColorFilter(Color.RED);
        }
        else {
            love.setColorFilter(getResources().getColor(android.R.color.darker_gray));
        }

        //if the user selects the heart then checks the state (for instance if is already on favorites) and shows the right message
        //αν γίνει κλικ στην καρδία έλεγχος της καταστασής της και εμφάνιση του αντίστοιχου μηνύματος στο χρήστη
        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFav = animal.getFavorite();
                animal.setFavorite(!isFav);

                //changes the state of the love button on the database
                //αλλαγή της κατάστασης του κουμπιού love στη βάση
                dbHandler.changeFavoriteState(animal);
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