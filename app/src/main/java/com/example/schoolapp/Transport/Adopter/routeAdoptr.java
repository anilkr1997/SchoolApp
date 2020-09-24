package com.example.schoolapp.Transport.Adopter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.Transport.modelclass.RoutModerlclass;

import java.util.List;

public class routeAdoptr extends RecyclerView.Adapter<routeAdoptr.MyViewHolder> {
    private FragmentActivity activity;
    private List<RoutModerlclass> list;
    public routeAdoptr(FragmentActivity activity, List<RoutModerlclass>list) {
        this.activity=activity;
        this.list=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.routelistitem,parent,false);
        return new routeAdoptr.MyViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Busnumber.setText(list.get(position).getBusNumber());
        holder.to.setText(list.get(position).getTo());
        holder.from.setText(list.get(position).getFrom());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Busnumber,to,from;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Busnumber=itemView.findViewById(R.id.busnumber);
            to=itemView.findViewById(R.id.directionTo);
            from=itemView.findViewById(R.id.directionFrom);
        }
    }
}
