package com.example.schoolapp.Transport.Adopter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.Transport.modelclass.Modeldriverassigne;

import java.util.List;

public class Driverassigneadopter extends RecyclerView.Adapter<Driverassigneadopter.MyViewHolder> {
   private FragmentActivity activity;
    private List<Modeldriverassigne> driverListmodelclasses;
    public Driverassigneadopter(FragmentActivity activity, List<Modeldriverassigne> driverListmodelclasses) {
        this.activity=activity;
        this.driverListmodelclasses=driverListmodelclasses;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.assignevechile,parent,false);
        return new Driverassigneadopter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(driverListmodelclasses.get(position).getId());
        holder.driverid.setText(driverListmodelclasses.get(position).getDriverID());
        holder.vechileid.setText(driverListmodelclasses.get(position).getVehicleID());

    }

    @Override
    public int getItemCount() {
        return driverListmodelclasses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
TextView driverid,vechileid,id;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            driverid=itemView.findViewById(R.id.asdriverid);
            vechileid=itemView.findViewById(R.id.vechileid);
            id=itemView.findViewById(R.id.listid);
        }
    }
}
