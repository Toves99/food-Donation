package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Estimate extends AppCompatActivity implements  RecycleViewInterface1 {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodapp-6b3b0-default-rtdb.firebaseio.com/");
    RecyclerView recyclerView;
    MyAdapter2 myAdapter;
    ArrayList<User2> list;
    public void onBackPressed(){
        super.onBackPressed();
        startActivity(new Intent(Estimate.this,BookEvent.class));
        finish();
    }
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate);
        recyclerView=findViewById(R.id.BookedList);
        databaseReference=FirebaseDatabase.getInstance().getReference("book");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        myAdapter=new MyAdapter2(this,list,this);
        recyclerView.setAdapter(myAdapter);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User2 user=dataSnapshot.getValue(User2.class);
                    list.add(user);
                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onItemClick(int position) {
        Intent intent=new Intent(Estimate.this,BookShareActivity.class);
        intent.putExtra("EventName",list.get(position).getEventName());
        intent.putExtra("EventDate",list.get(position).getDate());
        intent.putExtra("EventPlace",list.get(position).getPlace());
        startActivity(intent);
    }
}