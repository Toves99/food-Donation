package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BookShareActivity extends AppCompatActivity {
    private TextView textView8,textView10,textView12;
    private Button share,button;
    EditText etDonorName;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_share);
        textView8=findViewById(R.id.textView8);
        textView10=findViewById(R.id.textView10);
        textView12=findViewById(R.id.textView12);
        share=findViewById(R.id.share);
        button=findViewById(R.id.button);
        etDonorName=findViewById(R.id.etDonorName);

        String eventName=getIntent().getStringExtra("EventName");
        String eventDate=getIntent().getStringExtra("EventDate");
        String eventPlace=getIntent().getStringExtra("EventPlace");

        textView8.setText(eventName);
        textView10.setText(eventDate);
        textView12.setText(eventPlace);

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,textView12.getText().toString());
                intent.setType("text/plain");
                intent=Intent.createChooser(intent,"Share Via:");
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num=etDonorName.getText().toString();
                Intent intent=new Intent(BookShareActivity.this,NoPeople.class);
                intent.putExtra("number",num);
                startActivity(intent);
            }
        });
    }
}