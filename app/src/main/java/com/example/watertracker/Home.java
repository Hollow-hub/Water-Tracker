package com.example.watertracker;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;


public class Home extends AppCompatActivity {


    TextView MyStats;
    ProgressBar pb;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




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

    //edw prepei na to alla3w anti giaq timer na pairnei
    //ta cups pou kanei add o xrhsths
    public void prog(){
        pb = (ProgressBar)findViewById(R.id.progress_bar);
        final Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);

                if (counter == 100){
                    t.cancel();
                }
            }
        };

        t.schedule(tt,0,100);
    }
}
