package com.example.schoolapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Model.CreateFeeTypeModel;
import com.example.schoolapp.R;

import java.util.List;

public class CreateFeeTypeAdapter extends RecyclerView.Adapter<CreateFeeTypeAdapter.MyViewHolder> {
    List<CreateFeeTypeModel> createFeeTypeModelList;
    Activity activity;
    UpdateFeeType updateFeeType;

    public CreateFeeTypeAdapter(List<CreateFeeTypeModel> createFeeTypeModelList, Activity activity, UpdateFeeType updateFeeType) {
        this.createFeeTypeModelList = createFeeTypeModelList;
        this.activity = activity;
        this.updateFeeType = updateFeeType;
    }


    public interface UpdateFeeType{
        public void registerFeetype(String ID,String FeeType1,String IsActive,String IsDeleted);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.customfeetype,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.ID.setText("ID : "+createFeeTypeModelList.get(position).getID());
        holder.FeeType.setText("FeeType : "+createFeeTypeModelList.get(position).getFeeType1());
        holder.IsActive.setChecked(createFeeTypeModelList.get(position).isActive());
        holder.IsDeleted.setChecked(createFeeTypeModelList.get(position).isDeleted());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* updateFeeType.registerFeetype(createFeeTypeModelList.get(position).getID(),createFeeTypeModelList.get(position).getFeeType1(),
                        String.valueOf(holder.IsActive.isChecked()),  holder.IsDeleted.isChecked()+"");*/
                updateFeeType.registerFeetype(createFeeTypeModelList.get(position).getID(),
                        createFeeTypeModelList.get(position).getFeeType1(),
                        createFeeTypeModelList.get(position).isActive()+"",
                        createFeeTypeModelList.get(position).isDeleted()+"" );
            }
        });
    }

    @Override
    public int getItemCount() {
        return createFeeTypeModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ID,FeeType;
        CheckBox IsActive,IsDeleted;
        Button update;
        public MyViewHolder(View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.ID);
            FeeType=itemView.findViewById(R.id.FeeType1);
            IsActive=itemView.findViewById(R.id.IsActive);
            IsDeleted=itemView.findViewById(R.id.IsDeleted);
            update=itemView.findViewById(R.id.update);
        }
    }
}
