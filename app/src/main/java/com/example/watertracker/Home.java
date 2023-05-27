package com.example.watertracker;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.slider.Slider;
import com.google.android.material.textview.MaterialTextView;

import java.text.DecimalFormat;

public class Home extends AppCompatActivity {

    private static final String PREF_PROGRESS = "progress";
    private SharedPreferences sharedPreferences;
    CoreDatabase db;
    private String name;
    TextView username;
    TextView MyStats;
    double cups;
    Double Cups;
    Double Cups2;
    double Litre;
    TextView textView;
    private ProgressBar pb;
    private int CurrentProgress;
    private Button Add;
    private Button Reset;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        db = new CoreDatabase(this);
        //db.onCreate(db.getWritableDatabase());
        setContentView(R.layout.home);

        //We set the name of the user
        name = db.getUsername();
        username = findViewById(R.id.textViewName);
        username.setText(name);

        pb = findViewById(R.id.progress_bar);
        Add = findViewById(R.id.add);

        createNotificationChannel();

        //we set the cups so the user knows how many cups he should drink per day
        cups = db.getCups();
        textView = findViewById(R.id.textView3);
        cups = (int) cups;
        textView.setText(String.valueOf((int) cups));

        if (!db.Record_is_empty()){
            CurrentProgress = db.getRecord();
        }

        pb.setMax((int) cups);  // Set the maximum value of the progress bar
        pb.setProgress(CurrentProgress);  // Set the progress value

        //here we update the progressBar
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CurrentProgress = CurrentProgress + 1;
                pb.setProgress(CurrentProgress);
                db.insertRecord(CurrentProgress);

                //this is where we have the alarm manager for the reminder
                Intent intent = new Intent(Home.this, Reminder.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(Home.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                //this is where we put the exact time for the reminder
                long timeAtButtonClick = System.currentTimeMillis();
                long OneHour = 1000 * 3600;
                alarmManager.set(AlarmManager.RTC_WAKEUP, timeAtButtonClick + OneHour, pendingIntent);
            }
        });

        //Here we show the Dialog
        Button openDialogButton = findViewById(R.id.settings);
        openDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSliderDialog();
            }
        });

        //The reset button for when you conclude the progress
        Reset = findViewById(R.id.reset);
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pb.setProgress(0);
                Cups2 = db.getCups();
                DecimalFormat decimalFormat = new DecimalFormat("#");
                String cupsText = decimalFormat.format(Cups2);
                textView.setText(cupsText);
                CurrentProgress = 0;
                pb.setMax(Cups2.intValue());
                pb.setProgress(CurrentProgress);
            }
        });
    }

    //the dialog
    private void showSliderDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.slider, null);
        Slider weightSlider = dialogView.findViewById(R.id.weight_slider2);
        Slider activitySlider = dialogView.findViewById(R.id.activity_slider);

        builder.setView(dialogView);
        builder.setTitle("Change your Weight and Activity level");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //we get the value of the user about his weight and activity lvl
                float weightProgress = weightSlider.getValue();
                float activityProgress = activitySlider.getValue();
                //we calculate the cups that he needs
                Litre = (weightProgress / 30 ) + (activityProgress * 0.35);
                Cups = (Litre * 4.2);
                db.insertCups(Cups);
                DecimalFormat decimalFormat = new DecimalFormat("#");
                String cupsText = decimalFormat.format(Cups);
                textView.setText(cupsText);
                CurrentProgress = 0;
                pb.setMax(Cups.intValue());
                pb.setProgress(CurrentProgress);
            }
        });
        builder.setNegativeButton("Cancel", null);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();
    }



    //Here we create the channel for the reminder
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
