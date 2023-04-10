package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class StoreFood extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodapp-6b3b0-default-rtdb.firebaseio.com/");
    private EditText etFoodName;
    private EditText etPurchaseDate;
    private EditText etExpiryDate;
    private Button btnAdd;
    DatePickerDialog.OnDateSetListener setListener;

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(StoreFood.this, Dashboard.class));
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_food);
        etFoodName = findViewById(R.id.etFoodName);
        etPurchaseDate = findViewById(R.id.etPurchaseDate);
        etExpiryDate = findViewById(R.id.etExpiryDate);
        btnAdd = findViewById(R.id.btnAdd);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);



        etPurchaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        StoreFood.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        etPurchaseDate.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        etExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        StoreFood.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day + "/" + month + "/" + year;
                        etExpiryDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData();
            }
        });
    }

    public boolean validateFoodName() {
        String foodName = etFoodName.getText().toString();
        if (foodName.isEmpty()) {
            etFoodName.setError("This field can't be empty");
            etFoodName.requestFocus();
            return false;
        } else {
            etFoodName.setError(null);
            return true;
        }
    }

    public boolean validateDatePurchase() {
        String purchaseDate = etPurchaseDate.getText().toString();
        if (purchaseDate.isEmpty()) {
            etPurchaseDate.setError("The field can't be empty");
            etPurchaseDate.requestFocus();
            return false;
        } else {
            etPurchaseDate.setError(null);
            return true;
        }
    }

    public boolean validateDateExpiry() {
        String expiryDate = etExpiryDate.getText().toString();
        if (expiryDate.isEmpty()) {
            etExpiryDate.setError("The field can't be empty");
            etExpiryDate.requestFocus();
            return false;
        } else {
            etExpiryDate.setError(null);
            return true;
        }
    }

    public void addData() {

        String foodName = etFoodName.getText().toString();
        String purchaseDate = etPurchaseDate.getText().toString();
        String expiryDate = etExpiryDate.getText().toString();
        if (!validateFoodName() | !validateDatePurchase() | !validateDateExpiry()) {
            return;
        } else {
            databaseReference.child("KeepFood").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    databaseReference.child("KeepFood").child(foodName).child("foodName").setValue(foodName);
                    databaseReference.child("KeepFood").child(foodName).child("purchaseDate").setValue(purchaseDate);
                    databaseReference.child("KeepFood").child(foodName).child("expiryDate").setValue(expiryDate);
                    Toast.makeText(StoreFood.this,"Food Kept Successfully",Toast.LENGTH_SHORT).show();
                    etFoodName.setText("");
                    etPurchaseDate.setText("");
                    etExpiryDate.setText("");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(StoreFood.this,"Error Occurred",Toast.LENGTH_SHORT).show();

                }
            });

        }
    }

}