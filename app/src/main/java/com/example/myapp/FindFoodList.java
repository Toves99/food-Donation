package com.example.myapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FindFoodList extends AppCompatActivity implements  RecycleViewInterface{
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://foodapp-6b3b0-default-rtdb.firebaseio.com/");
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    ArrayList<User> list;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(FindFoodList.this,Dashboard.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_food_list);
        recyclerView=findViewById(R.id.findFoodList);
        databaseReference=FirebaseDatabase.getInstance().getReference("donate");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        myAdapter=new MyAdapter(this,list,this);
        recyclerView.setAdapter(myAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    User user=dataSnapshot.getValue(User.class);
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
        Intent intent=new Intent(FindFoodList.this,CollectFood.class);
        intent.putExtra("DonorName",list.get(position).getDonorName());
        intent.putExtra("DonorLocation",list.get(position).getDonorLocation());
        intent.putExtra("FoodTitle",list.get(position).getFoodTitle());
        intent.putExtra("FoodCategory",list.get(position).getFoodCategory());
        intent.putExtra("FoodQuantity",list.get(position).getFoodQuantity());
        intent.putExtra("ExpiryDate",list.get(position).getExpiryDate());
        startActivity(intent);
    }
}