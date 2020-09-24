package com.example.schoolapp.Transport.Adopter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.Transport.modelclass.TDModelClass;

public class TDAdopter extends RecyclerView.Adapter<TDAdopter.MyViewHolder> {
    Activity activity;
    TDModelClass[] tdModelClass;
    public TDAdopter(Activity activitya, TDModelClass[] tdModelClass) {
        this.activity=activitya;
        this.tdModelClass=tdModelClass;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.recycyleviewitem,parent,false);
        return new TDAdopter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TDModelClass homeitem= tdModelClass[position];
        holder.textView.setText(tdModelClass[position].getDasboardName());
        holder.imageView.setImageResource(tdModelClass[position].getImage());
    }

    @Override
    public int getItemCount() {
        return tdModelClass.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.itemname);
            imageView=itemView.findViewById(R.id.tacking);
        }
    }
}
