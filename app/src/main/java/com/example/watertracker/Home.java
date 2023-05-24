package com.example.watertracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class Home extends AppCompatActivity {


    CoreDatabase db;
    private String name;
    TextView username;
    TextView MyStats;
    private ProgressBar pb;
    private int CurrentProgress = 0;
    private Button Add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new CoreDatabase(this);
        db.onCreate(db.getWritableDatabase());
        setContentView(R.layout.home);

        name = db.getUsername();
        username = findViewById(R.id.textView3);
        username.setText(name);


        pb = findViewById(R.id.progress_bar);
        Add = findViewById(R.id.add);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentProgress = CurrentProgress + 10;
                pb.setProgress(CurrentProgress);
                pb.setMax(100);
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
