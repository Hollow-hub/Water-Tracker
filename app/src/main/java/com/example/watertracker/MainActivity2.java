package com.example.watertracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;

public class MainActivity2 extends AppCompatActivity{

    CoreDatabase db;
    Slider rangeSliderWeight,rangeSliderActivity;
    double Weight = 40.0;
    int Activity = 1;
    Double Cups;
    double Litre;
    int x = 0;
    int y = 0;
    ImageView imageView;
    Drawable drawable;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new CoreDatabase(this);
        db.onCreate(db.getWritableDatabase());
        setContentView(R.layout.activity_main2);

        imageView=findViewById(R.id.imageView);
        drawable = getResources().getDrawable(R.drawable.water);
        imageView.setImageDrawable(drawable);

        rangeSliderWeight = findViewById(R.id.Weight_slider);
        rangeSliderActivity = findViewById(R.id.Activity_slider);

        // First Slider for the weight
        rangeSliderWeight.addOnChangeListener((slider, value, fromUser) -> {
            Weight = value;
            x = 1;
        });

        //Second Slider for the activity level
        rangeSliderActivity.addOnChangeListener((slider, value, fromUser) -> {
            Activity = (int)value;
            y = 1;
        });

        configureNextButton();
    }

    private void configureNextButton(){
        Button nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(v -> {
            //Just some checks for the user
            if ((x | y) == 0){
                Toast.makeText(MainActivity2.this, "Make sure to put your weight and activity level", Toast.LENGTH_LONG).show();
            }else{
                if (Weight == 0)
                    Toast.makeText(MainActivity2.this, "Make sure not to put 0kg to your weight", Toast.LENGTH_LONG).show();
                else
                    // calculate the the cups
                    Litre = (Weight / 30 ) + (Activity * 0.35);
                    Cups = (Litre * 4.2);
                    db.insertCups(Cups);
                    startActivity(new Intent(MainActivity2.this, Home.class));
            }
        });
    }
}
