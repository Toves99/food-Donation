package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;


public class AddFood extends AppCompatActivity {
    // variable declaration
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodapp-6b3b0-default-rtdb.firebaseio.com/");

    private EditText etDonorName,etDonorLocation,etFoodTitle,etFoodCategory,etFoodQuantity,etExpiryDate;
    private Button btnSubmitDonation;
    ProgressDialog progressDialog;
    Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);

    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(AddFood.this, DonateFood.class));
        finish();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        // Variable initialization
        etDonorName=findViewById(R.id.etDonorName);
        etDonorLocation=findViewById(R.id.etDonorLocation);
        etFoodTitle=findViewById(R.id.etFoodTitle);
        etFoodQuantity=findViewById(R.id.etFoodQuantity);
        etFoodCategory=findViewById(R.id.etFoodCategory);
        etExpiryDate=findViewById(R.id.etExpiryDate);
        btnSubmitDonation=findViewById(R.id.btnSubmitDonation);
        //onclick method to create a calender dialog that pops up when date editText is clicked
        etExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddFood.this, new DatePickerDialog.OnDateSetListener() {
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
        //onclick method to add food to the realtime firebase database
        btnSubmitDonation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String donorName=etDonorName.getText().toString();
                final String donorLocation=etDonorLocation.getText().toString();
                final String foodTitle=etFoodTitle.getText().toString();
                final String foodQuantity=etFoodQuantity.getText().toString();
                final String foodCategory=etFoodCategory.getText().toString();
                final String expiryDate=etExpiryDate.getText().toString();
                if(donorName.isEmpty()|| donorLocation.isEmpty()|| foodTitle.isEmpty()|| foodQuantity.isEmpty()|| foodCategory.isEmpty()|| expiryDate.isEmpty()){
                    Toast.makeText(AddFood.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("donate").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            databaseReference.child("donate").child(donorName).child("donorName").setValue(donorName);
                            databaseReference.child("donate").child(donorName).child("donorLocation").setValue(donorLocation);
                            databaseReference.child("donate").child(donorName).child("foodTitle").setValue(foodTitle);
                            databaseReference.child("donate").child(donorName).child("foodQuantity").setValue(foodQuantity);
                            databaseReference.child("donate").child(donorName).child("foodCategory").setValue(foodCategory);
                            databaseReference.child("donate").child(donorName).child("expiryDate").setValue(expiryDate);
                            Toast.makeText(AddFood.this, "Food Donated Successfully",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(AddFood.this,"An Error Occurred",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

}
