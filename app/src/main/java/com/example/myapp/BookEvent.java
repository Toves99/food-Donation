package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BookEvent extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodapp-6b3b0-default-rtdb.firebaseio.com/");
    private EditText etEventName;
    private EditText etEventDate;
    private EditText etEventPlace;
    private EditText etNumberPeople;
    ProgressDialog progressDialog;
    private Button save, next;
    int year;
    int month;
    int day;


    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(BookEvent.this, Dashboard.class));
        finish();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_event);
        Calendar calendar = Calendar.getInstance();
        etEventName = findViewById(R.id.etEventName);
        etEventDate = findViewById(R.id.etEventDate);
        etEventPlace = findViewById(R.id.etEventPlace);
        etNumberPeople = findViewById(R.id.etNumberPeople);
        save = findViewById(R.id.save);
        next = findViewById(R.id.next);
        progressDialog = new ProgressDialog(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        etEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookEvent.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        etEventDate.setText(SimpleDateFormat.getInstance().format(calendar.getTime()));

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextEvent();
            }
        });

    }

    public boolean validateEventName() {
        String eventName = etEventName.getText().toString();
        if (eventName.isEmpty()) {
            etEventName.setError("Field can't be empty");
            etEventName.requestFocus();
            return false;
        } else {
            etEventName.setError(null);
            return true;
        }
    }

    public boolean validateDate() {
        String date = etEventDate.getText().toString();
        if (date.isEmpty()) {
            etEventDate.setError("Field can't be empty");
            etEventDate.requestFocus();
            return false;
        } else {
            etEventDate.setError(null);
            return true;
        }
    }

    public boolean validatePlace() {
        String place = etEventPlace.getText().toString();
        if (place.isEmpty()) {
            etEventPlace.setError("Field can't be empty");
            etEventPlace.requestFocus();
            return false;
        } else {
            etEventPlace.setError(null);
            return true;
        }
    }

    public boolean validateNumber() {
        String number = etNumberPeople.getText().toString();
        if (number.isEmpty()) {
            etNumberPeople.setError("Field can't be empty");
            etNumberPeople.requestFocus();
            return false;
        } else {
            etNumberPeople.setError(null);
            return true;
        }
    }

    public void save() {
        String eventName = etEventName.getText().toString();
        String date = etEventDate.getText().toString();
        String place = etEventPlace.getText().toString();
        String number = etNumberPeople.getText().toString();
        if (!validateEventName() | !validateDate() | !validatePlace() | !validateNumber()) {
            return;
        } else {
            databaseReference.child("book").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    databaseReference.child("book").child(eventName).child("eventName").setValue(eventName);
                    databaseReference.child("book").child(eventName).child("date").setValue(date);
                    databaseReference.child("book").child(eventName).child("place").setValue(place);
                    databaseReference.child("book").child(eventName).child("number").setValue(number);
                    progressDialog.setMessage("Booking....");
                    progressDialog.setTitle("Please Wait...");
                    progressDialog.setIndeterminate(true);
                    Toast.makeText(BookEvent.this, "Booking completed successfully", Toast.LENGTH_SHORT).show();
                    etEventDate.setText("");
                    etEventPlace.setText("");
                    etNumberPeople.setText("");

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(BookEvent.this, "Error Occurred", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void nextEvent() {
        startActivity(new Intent(BookEvent.this,Estimate.class));
        finish();
    }
}