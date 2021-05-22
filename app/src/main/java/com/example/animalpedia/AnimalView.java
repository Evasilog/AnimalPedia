package com.example.animalpedia;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class AnimalView extends AppCompatActivity {

    Animal an;
    TextView anName;
    ImageView anImage;
    TextView anClass;
    TextView anContinent;
    TextView anDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_view);

        anName = findViewById(R.id.an_name);
        anImage = findViewById(R.id.an_image);
        anClass = findViewById(R.id.an_class);
        anContinent = findViewById(R.id.an_continent);
        anDetails = findViewById(R.id.an_details);

        an = getIntent().getParcelableExtra("AnimalId");

        anName.setText(an.getName());

        byte[] img = an.getImage();
        Bitmap image = BitmapFactory.decodeByteArray(img, 0, img.length);
        anImage.setImageBitmap(image);
        anClass.setText(an.getAnimalClass());
        anContinent.setText(an.getContinent());
        anDetails.setText(an.getDetails());

    }

}