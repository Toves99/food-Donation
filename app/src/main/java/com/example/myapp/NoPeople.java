package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NoPeople extends AppCompatActivity {
    TextView textView4;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NoPeople.this,BookShareActivity.class));
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_people);
        textView4=findViewById(R.id.textView4);

        String numb=getIntent().getStringExtra("number");
        textView4.setText(numb);
    }
}