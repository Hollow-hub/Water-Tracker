package com.example.watertracker;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
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
    private int CurrentProgress;
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

        createNotificationChannel();

        cups = db.getCups();
        textView = findViewById(R.id.textView3);
        cups = (int)cups;
        textView.setText(String.valueOf((int) cups));
        if (!db.Record_is_empty()){
            CurrentProgress = db.getRecord();
            pb.setProgress(CurrentProgress);
        }


        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentProgress = CurrentProgress + 1;
                pb.setProgress(CurrentProgress);
                pb.setMax((int) cups);
                db.insertRecord(CurrentProgress);
                Intent intent = new Intent(Home.this,Reminder.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(Home.this,0,intent,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                long timeAtButtonClick = System.currentTimeMillis();
                long OneHour = 1000 * 3600;
                alarmManager.set(AlarmManager.RTC_WAKEUP,timeAtButtonClick + OneHour,pendingIntent);
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

    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "ReminderChannel";
            String description = "Channel for Reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Notify",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
