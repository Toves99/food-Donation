package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard extends AppCompatActivity {
    // variable declaration
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodapp-6b3b0-default-rtdb.firebaseio.com/\"");
    Button btnDonations,btnFindFood,btn_date,btnBook,btnStore;
    TextView date;
    private int countRequest=0;

    /**
     * Developed by clinton Tovesi
     * This is the dashboard
     */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Dashboard.this,MainActivity.class));
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Variable initilization
        setContentView(R.layout.activity_dashboard);
        btnDonations=findViewById(R.id.btnDonations);
        btnFindFood=findViewById(R.id.btnFindFood);
        btn_date=findViewById(R.id.btn_date);
        btnBook=findViewById(R.id.btnBook);
        btnStore=findViewById(R.id.btnStore);
        date=findViewById(R.id.date);
        // Onclick event that takes you to the Donation activity
        btnDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,DonateFood.class));
                finish();
            }
        });
        // onClick event that takes you to the Find food activity
        btnFindFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,FindFoodList.class));
                finish();
            }
        });
        //Fetching date alert from the firebase database
        databaseReference=FirebaseDatabase.getInstance().getReference().child("KeepFood");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    countRequest=(int) snapshot.getChildrenCount();
                    date.setText(Integer.toString(countRequest)+".Alert");
                }
                else {
                    date.setText("0.Alert");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // onClick event that takes you to view expiry date alert
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,ExpiryDateList.class));
                finish();
            }
        });
        // onClick event that takes you to Book event
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,BookEvent.class));
                finish();
            }
        });
        // onClick event that takes you to store food
        btnStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,StoreFood.class));
                finish();
            }
        });
    }

}