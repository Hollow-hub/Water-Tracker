package com.example.watertracker;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

public class MainActivity2 extends AppCompatActivity{

    CoreDatabase db;
    RangeSlider rangeSliderWeight,rangeSliderActivity;
    double Weight = 40.0;
    int Activity = 1;

    int x = 0;
    int y = 0;
    ImageView imageView;
    Drawable drawable;

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

        rangeSliderWeight.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Responds to when slider's value is changed
                Weight = value;
                x = 1;
            }
        });

        rangeSliderActivity.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Responds to when slider's value is changed
                Activity = (int)value;
                y = 1;
            }
        });

        configureNextButton();

        // calculate the the cups
        double Litre = (Weight * 0.02957) + (Activity * 12 );
        int Cups = (int) (Litre * 4.2);

    }
    private void configureNextButton(){
        Button nextButton = findViewById(R.id.buttonNext);
        nextButton.setOnClickListener(v -> {
            if ((x | y) == 0){
                Toast.makeText(MainActivity2.this, "Make sure to put your weight and activity level", Toast.LENGTH_LONG).show();
            }else{
                startActivity(new Intent(MainActivity2.this, Home.class));
            }
        });
    }
}
