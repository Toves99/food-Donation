package com.example.myapp;



import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;


public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder> {

    Context context;
    ArrayList<User1> list;

    public MyAdapter1(Context context, ArrayList<User1> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item1,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User1 user=list.get(position);
        holder.etExpiryDate.setText(user.getExpiryDate());
        holder.etFoodName.setText(user.getFoodName());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView etExpiryDate;
        TextView etFoodName;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            etExpiryDate = itemView.findViewById(R.id.etExpiryDate);
            etFoodName=itemView.findViewById(R.id.etFoodName);


        }
    }
}

