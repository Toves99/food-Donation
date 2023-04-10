package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Developed by clinton Toves
 * This is the login page
 */
public class Login extends AppCompatActivity {
    // variable declaration
    private EditText etEmail, etPassword;
    private Button btnLogIn, btnCreateAccount;
    private FirebaseAuth mAuth;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Variable initialization
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        //On click Event when clicked it takes you to the registration page
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,UserRegistration.class));
                finish();
            }
        });

        // When onClicked event
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    //Method to validate email

    public boolean validateEmail() {
        String email = etEmail.getText().toString();
        if (email.isEmpty()) {
            etEmail.setError("Please enter your email");
            etEmail.requestFocus();
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }
    // method to validate password
    public boolean validatePassword() {
        String password = etPassword.getText().toString();
        if (password.isEmpty()) {
            etPassword.setError("Please enter your password");
            etPassword.requestFocus();
            return false;
        } else {
            etPassword.setError(null);
            return true;
        }
    }

    /**
     *  method when invoked it checks whether user inputs are correct then it checks whether
     *  input from the user matches the one in the database if it matches the user logs in else the user is denied
     *  to login
     */


    public void login() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        if (!validateEmail() | !validatePassword()) {
            return;
        } else {
            progressDialog.setTitle("Signing In.....");
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> {
                        progressDialog.cancel();
                        Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Dashboard.class));
                        finish();
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.cancel();
                            Toast.makeText(Login.this, "Check your Details and try again", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}