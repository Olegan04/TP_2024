package com.example.pushuptrecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity {
    private Button submitBtn;
    private Button alreadyRegisteredBtn;
    private EditText loginEdt;
    private EditText passwordEdt;
    private EditText confirmPasswordEdt;
    private EditText nameEdt;
    private EditText ageEdt;
    private EditText sexEdt;
    private EditText locationEdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loginEdt = findViewById(R.id.form_login);
        passwordEdt = findViewById(R.id.form_password);
        confirmPasswordEdt = findViewById(R.id.form_confirm_password);
        nameEdt = findViewById(R.id.form_name);
        ageEdt = findViewById(R.id.form_age);
        sexEdt = findViewById(R.id.form_sex);
        locationEdt = findViewById(R.id.form_location);
        submitBtn = findViewById(R.id.submitBtn);
        alreadyRegisteredBtn = findViewById(R.id.alreadyRegisteredBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        alreadyRegisteredBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
            }
        });
    }
}