package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CollectFood extends AppCompatActivity {
    private TextView textView8,textView10,textView12,textView14,textView16,textView18;
    EditText etAdress;
    Button collect;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_food);

        textView8=findViewById(R.id.textView8);
        textView10=findViewById(R.id.textView10);
        textView12=findViewById(R.id.textView12);
        textView14=findViewById(R.id.textView14);
        textView16=findViewById(R.id.textView16);
        textView18=findViewById(R.id.textView18);
        etAdress=findViewById(R.id.etAdress);
        collect=findViewById(R.id.collect);

        String donorName=getIntent().getStringExtra("DonorName");
        String donorLocation=getIntent().getStringExtra("DonorLocation");
        String foodTitle=getIntent().getStringExtra("FoodTitle");
        String foodCategory=getIntent().getStringExtra("FoodCategory");
        String foodQuantity=getIntent().getStringExtra("FoodQuantity");
        String expiry=getIntent().getStringExtra("ExpiryDate");

        textView8.setText(donorName);
        textView10.setText(donorLocation);
        textView12.setText(foodTitle);
        textView14.setText(foodCategory);
        textView16.setText(foodQuantity);
        textView18.setText(expiry);

        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CollectFood.this,"You have collected this food wait to be delivered",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CollectFood.this,Dashboard.class));
                finish();
            }
        });
    }
}