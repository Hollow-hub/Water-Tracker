package com.example.watertracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    CoreDatabase db;
    EditText nameInput;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView username = (TextView) findViewById(R.id.nickname);
        TextView login = (TextView) findViewById(R.id.signin);


        if (!db.Usernames_is_empty()) {
            startActivity(new Intent(MainActivity.this, Home.class));
        }

        setContentView(R.layout.activity_main);
        nameInput = findViewById(R.id.nickname);

        configureNextButton();

    }
    private void configureNextButton(){
        Button nextButton = (Button) findViewById(R.id.loginbtn);
        nextButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(nameInput.getText().toString())){
                Toast.makeText(MainActivity.this, "Make sure to write a nickname", Toast.LENGTH_LONG).show();
            }else{
                // get the nickname and add it to the database
                name = nameInput.getText().toString();
                // add nickname to database
                // if this crushes, it's because the name exists in database
                db.insertUsername(name);
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });
    }
}