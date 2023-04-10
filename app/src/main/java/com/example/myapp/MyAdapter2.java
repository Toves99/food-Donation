package com.example.myapp;



import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;


import java.io.File;
import java.util.ArrayList;


public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.MyViewHolder> {
    private final RecycleViewInterface1 recycleViewInterface1;
    Context context;
    ArrayList<User2> list;

    public MyAdapter2(Context context, ArrayList<User2> list,RecycleViewInterface1 recycleViewInterface1) {
        this.context = context;
        this.list = list;
        this.recycleViewInterface1=recycleViewInterface1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
        return new MyViewHolder(v,recycleViewInterface1);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User2 user=list.get(position);
        holder.etEventName.setText(user.getEventName());
        holder.etEventDate.setText(user.getDate());
        holder.etEventPlace.setText(user.getPlace());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView etEventName;
        TextView etEventDate;
        TextView etEventPlace;



        public MyViewHolder(@NonNull View itemView,RecycleViewInterface1 recycleViewInterface1) {
            super(itemView);
            etEventName = itemView.findViewById(R.id.etEventName);
            etEventDate=itemView.findViewById(R.id.etEventDate);
            etEventPlace=itemView.findViewById(R.id.etEventPlace);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycleViewInterface1 !=null){
                        int pos=getAdapterPosition();

                        if(pos !=RecyclerView.NO_POSITION){
                            recycleViewInterface1.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}

