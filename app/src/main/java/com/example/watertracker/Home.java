package com.example.watertracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

public class Home extends AppCompatActivity {

    CoreDatabase db;
    private String name;
    TextView username;
    TextView MyStats;
    double cups;
    TextView textView;
    private ProgressBar pb;
    private double CurrentProgress = 0;
    private Button Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new CoreDatabase(this);
        //db.onCreate(db.getWritableDatabase());
        setContentView(R.layout.home);

        name = db.getUsername();
        username = findViewById(R.id.textViewName);
        username.setText(name);


        pb = findViewById(R.id.progress_bar);
        Add = findViewById(R.id.add);

        cups = db.getCups();
        textView = findViewById(R.id.textView3);
        cups = (int)cups;
        textView.setText(String.valueOf(cups));


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //db.insertRecord();
                CurrentProgress = CurrentProgress + 1;
                pb.setProgress((int) CurrentProgress);
                pb.setMax((int) cups);
            }
        });


        MyStats = findViewById(R.id.stats);
        MyStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Home.this);
                dialog.setTitle("Current Stats");
                dialog.setCancelable(true);
                dialog.show();
            }
        });


    }
}
