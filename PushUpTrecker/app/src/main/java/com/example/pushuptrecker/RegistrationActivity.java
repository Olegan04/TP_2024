package com.example.pushuptrecker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

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
        loginEdt = findViewById(R.id.formLoginEdt);
        passwordEdt = findViewById(R.id.formPasswordEdt);
        confirmPasswordEdt = findViewById(R.id.formConfirmPasswordEdt);
        nameEdt = findViewById(R.id.formNameEdt);
        ageEdt = findViewById(R.id.formAgeEdt);
        sexEdt = findViewById(R.id.formSexEdt);
        locationEdt = findViewById(R.id.formLocationEdt);

        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(v ->{

        });
        alreadyRegisteredBtn = findViewById(R.id.alreadyRegisteredBtn);
        alreadyRegisteredBtn.setOnClickListener(v->{
            Intent i = new Intent();
        });
    }
}