package com.example.schoolapp.Transport.Adopter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.Transport.modelclass.DriverListmodelclass;

import java.util.List;

public class DriverAdopter extends RecyclerView.Adapter<DriverAdopter.MyViewHolder> {
   private FragmentActivity activity;
   private List<DriverListmodelclass> driverListmodelclasses;
    public DriverAdopter(FragmentActivity activity, List<DriverListmodelclass> driverListmodelclasses) {
        this.activity=activity;
        this.driverListmodelclasses=driverListmodelclasses;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.driverlistltem,parent,false);
        return new DriverAdopter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.ID.setText(driverListmodelclasses.get(position).getID());
        holder.Name.setText(driverListmodelclasses.get(position).getDriverName());
        holder.phone.setText(driverListmodelclasses.get(position).getPhone());
        holder.email.setText(driverListmodelclasses.get(position).getEmail());
        holder.Addres.setText(driverListmodelclasses.get(position).getAddress());


    }

    @Override
    public int getItemCount() {
        return driverListmodelclasses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView Name,email,phone,ID,Addres;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=itemView.findViewById(R.id.DriverName);
            email=itemView.findViewById(R.id.DriverEmail);
            phone=itemView.findViewById(R.id.DriverPhone);
            ID=itemView.findViewById(R.id.Driverid);
            Addres=itemView.findViewById(R.id.DriverAddress);
            imageView=itemView.findViewById(R.id.ImagePath);
        }
    }
}
