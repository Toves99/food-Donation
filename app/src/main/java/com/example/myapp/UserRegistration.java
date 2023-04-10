package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.regex.Pattern;

/**
 * Developed by clinton Tovesi
 * This is the registration page
 */
public class UserRegistration extends AppCompatActivity {
    //Variable declaration
    private EditText etEmail, etPassword, etConfPassword;
    private Button register_btn;
    private FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    ProgressDialog progressDialog;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$"
            );

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        // Variable initialization
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfPassword = findViewById(R.id.etConfPassword);
        register_btn = findViewById(R.id.register_btn);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        progressDialog=new ProgressDialog(this);
        //function_registration
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }
    // method to validate user email
    private boolean validateEmail() {
        String email = etEmail.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Enter your email please");
            etEmail.requestFocus();
            return false;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please enter a valid email");
            etEmail.requestFocus();
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }
    // method to validate user password
    private boolean validatePassword() {
        String password = etPassword.getText().toString().trim();
        if (password.isEmpty()) {
            etPassword.setError("Field can not be empty");
            etPassword.requestFocus();
            return false;
        }  else if(!PASSWORD_PATTERN.matcher(password).matches()){
            etPassword.setError("Please enter a strong password");
            etPassword.requestFocus();
            return false;
        }
        else if(password.length()<8){
            etPassword.setError("Password is too short");
            etPassword.requestFocus();
            return false;
        }
        else {
            etPassword.setError(null);
            return true;
        }
    }

    /**
     * Method to register user into the system first it checks whether user inputs are correct then
     * it register user
     */
    public void registerUser(){
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confPassword=etConfPassword.getText().toString();
        if(!validateEmail()|!validatePassword()){
            return;
        }
        else if(!password.equals(confPassword)){
            Toast.makeText(UserRegistration.this, "Password is not matching...", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setTitle("Creating account........");
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            startActivity(new Intent(UserRegistration.this,MainActivity.class));
                            finish();
                            progressDialog.cancel();
                            firebaseFirestore.collection("user")
                                    .document(FirebaseAuth.getInstance().getUid())
                                    .set(new UserMode(email));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(UserRegistration.this,"Check your details or check your network connection and try again",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    }
