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

public class AnimalView extends AppCompatActivity {
    public MyDBHandler dbHandler;
    Animal an;
    TextView anName;
    ImageView anImage;
    TextView anClass;
    TextView anContinent;
    ImageView youtube;
    ImageView love;
    TextView anDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_view);

        anName = findViewById(R.id.an_name);
        anImage = findViewById(R.id.an_image);
        anClass = findViewById(R.id.an_class);
        anContinent = findViewById(R.id.an_continent);
        youtube = findViewById(R.id.youtube);
        love = findViewById(R.id.love);
        anDetails = findViewById(R.id.an_details);

        an = getIntent().getParcelableExtra("AnimalId");

        anName.setText(an.getName());

        byte[] img = an.getImage();
        Bitmap image = BitmapFactory.decodeByteArray(img, 0, img.length);
        anImage.setImageBitmap(image);
        anClass.setText(an.getAnimalClass());
        anContinent.setText(an.getContinent());
        anDetails.setText(an.getDetails());
        anDetails.setMovementMethod(new ScrollingMovementMethod());
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(an.getLink());
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });

        dbHandler = new MyDBHandler(this, null);
        if (an.getFavorite()){
            love.setColorFilter(Color.RED);
        }
        else {
            love.setColorFilter(getResources().getColor(android.R.color.darker_gray));
        }

        love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFav = an.getFavorite();
                an.setFavorite(!isFav);
                dbHandler.changeFavoriteState(an);
                if (isFav){
                    love.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                    Toast.makeText(getApplicationContext(),"Animal removed from your favorites",Toast.LENGTH_LONG).show();
                }
                else {
                    love.setColorFilter(Color.RED);
                    Toast.makeText(getApplicationContext(),"Animal added to your favorites!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

}