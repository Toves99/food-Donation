package com.example.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<User> list;
    private final RecycleViewInterface recycleViewInterface;

    public MyAdapter(Context context, ArrayList<User> list,
                     RecycleViewInterface recycleViewInterface) {
        this.context = context;
        this.list = list;
        this.recycleViewInterface=recycleViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyAdapter.MyViewHolder(v,recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User user=list.get(position);
        holder.etDonorName.setText(user.getDonorName());
        holder.etDonorLocation.setText(user.getDonorLocation());
        holder.etFoodTitle.setText(user.getFoodTitle());
        holder.etFoodCategory.setText(user.getFoodCategory());
        holder.etFoodQuantity.setText(user.getFoodQuantity());
        holder.etExpiryDate.setText(user.getExpiryDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView etDonorName,etDonorLocation,etFoodTitle,etFoodCategory,etFoodQuantity,etExpiryDate;
        Button collect;
        public MyViewHolder(@NonNull View itemView,RecycleViewInterface recycleViewInterface) {
            super(itemView);
            etDonorName=itemView.findViewById(R.id.etDonorName);
            etDonorLocation=itemView.findViewById(R.id.etDonorLocation);
            etFoodTitle=itemView.findViewById(R.id.etFoodTitle);
            etFoodCategory=itemView.findViewById(R.id.etFoodCategory);
            etFoodQuantity=itemView.findViewById(R.id.etFoodQuantity);
            etExpiryDate=itemView.findViewById(R.id.etExpiryDate);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(recycleViewInterface !=null){
                        int pos=getAdapterPosition();

                        if(pos !=RecyclerView.NO_POSITION){
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
