package com.example.watertracker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.text.NumberFormat;
import java.util.Currency;

public class MainActivity2 extends AppCompatActivity {

    RangeSlider rangeSliderWeight,rangeSliderActivity;

    ImageView imageView;
    Drawable drawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        rangeSliderWeight = findViewById(R.id.Weight_slider);
        rangeSliderActivity = findViewById(R.id.Activity_slider);

        rangeSliderWeight.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Responds to when slider's value is changed

            }
        });

        rangeSliderActivity.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                // Responds to when slider's value is changed

            }
        });

        imageView=findViewById(R.id.imageView);
        drawable = getResources().getDrawable(R.drawable.water);
        imageView.setImageDrawable(drawable);
    }
}
