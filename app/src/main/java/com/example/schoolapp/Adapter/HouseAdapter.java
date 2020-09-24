package com.example.schoolapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Model.ClassModel;
import com.example.schoolapp.Model.HouseModel;
import com.example.schoolapp.R;

import java.util.List;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.MyViewHolder> {
    public interface UpdateHouse{
        public void registerClass(String ID,String HouseName,String IsActive,String IsDeleted);
    }


    List<HouseModel> houseModelList;
    Context context;

    public HouseAdapter(List<HouseModel> houseModelList, Context context, UpdateHouse update) {
        this.houseModelList = houseModelList;
        this.context = context;
        this.update = update;
    }

    UpdateHouse update;

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customhouse,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.ID.setText("ID : "+houseModelList.get(position).getID());
        holder.house.setText("House : "+houseModelList.get(position).getHouseName());
        holder.IsActive.setChecked(houseModelList.get(position).isActive());
        holder.IsDeleted.setChecked(houseModelList.get(position).isDeleted());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.registerClass(houseModelList.get(position).getID(),houseModelList.get(position).getHouseName(),
                        String.valueOf(holder.IsActive.isChecked()),  String.valueOf(holder.IsDeleted.isChecked()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return houseModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ID,house;
        CheckBox IsActive,IsDeleted;
        Button update;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.ID);
            house=itemView.findViewById(R.id.house);
            IsActive=itemView.findViewById(R.id.IsActive);
            IsDeleted=itemView.findViewById(R.id.IsDeleted);
            update=itemView.findViewById(R.id.update);
        }
    }
}
