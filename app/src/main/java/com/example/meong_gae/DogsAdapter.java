package com.example.meong_gae;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DogsAdapter extends RecyclerView.Adapter<DogsAdapter.ViewHolder> {

    ArrayList<Dogs> dogsArrayList;

    public DogsAdapter(ArrayList<Dogs> dogsArrayList)
    {
        this.dogsArrayList = dogsArrayList;
    }
    @Override
    public DogsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mydog_recycle_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder( DogsAdapter.ViewHolder holder, int position) {

        holder.img.setImageResource(dogsArrayList.get(position).getImageResId());
        holder.name.setText(dogsArrayList.get(position).getDogname());
        holder.age.setText(dogsArrayList.get(position).getDogage());
        holder.sex.setText(dogsArrayList.get(position).getDogsex());
        holder.size.setText(dogsArrayList.get(position).getDogsize());

    }

    @Override
    public int getItemCount() {
        return dogsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView age;
        TextView sex;
        TextView size;
        ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img_dog);
            name = itemView.findViewById(R.id.tv_name);
            age = itemView.findViewById(R.id.dog_age);
            sex = itemView.findViewById(R.id.dog_sex);
            size = itemView.findViewById(R.id.dog_size);


        }
    }
}
