package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DonateFood extends AppCompatActivity {
    FloatingActionButton addFood;
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(DonateFood.this,Dashboard.class));
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_food);
        addFood=findViewById(R.id.addFood);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DonateFood.this,AddFood.class));
                finish();
            }
        });
    }
}