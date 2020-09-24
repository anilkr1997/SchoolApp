package com.example.schoolapp.Transport.Adopter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.Transport.modelclass.VehicleListModelClass;

import java.util.List;

public class ListofVachileAdopter extends RecyclerView.Adapter<ListofVachileAdopter.MyViewHolder>{
  private FragmentActivity activity;
   private List<VehicleListModelClass> list;
    public ListofVachileAdopter(FragmentActivity activity, List<VehicleListModelClass> VehicleListModelClass) {
        this.activity=activity;
        this.list=VehicleListModelClass;
    }
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.vehiclelistitem,parent,false);
        return new ListofVachileAdopter.MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.busnumber.setText(list.get(position).getVehicleNo());
        holder.busrcnumber.setText(list.get(position).getRcNo());
        holder.ownerphonenumber.setText(list.get(position).getPhoneNo());
       // holder.busid.setText(list.get(position).getID());
        holder.ownername.setText(list.get(position).getWonerName());
        holder.owneradress.setText(list.get(position).getWonerAddress());
        holder.vechiletype.setText(list.get(position).getVehicleTypeId());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView busnumber,busrcnumber,ownerphonenumber,busid,ownername,owneradress,vechiletype;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            busnumber=itemView.findViewById(R.id.busnumber);
            busrcnumber=itemView.findViewById(R.id.busRcnumber);
            ownerphonenumber=itemView.findViewById(R.id.ownerphonenumber);
           // busid=itemView.findViewById(R.id.busid);
            ownername=itemView.findViewById(R.id.ownername);
            owneradress=itemView.findViewById(R.id.ownerAddres);
            vechiletype=itemView.findViewById(R.id.vechiletypeid);
        }
    }
}
