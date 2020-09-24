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
import com.example.schoolapp.Model.SectionModel;
import com.example.schoolapp.R;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.MyViewHolder> {
    List<SectionModel> sectionModelList;
    Context context;

    public SectionAdapter(List<SectionModel> sectionModelList, Context context, UpdateSection update) {
        this.sectionModelList = sectionModelList;
        this.context = context;
        this.update = update;
    }

    UpdateSection update;
    public interface UpdateSection{
        public void registerSection(String ID,String SectionName,String IsActive,String IsDeleted);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customsection,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.ID.setText("ID : "+sectionModelList.get(position).getID());
        holder.SectionName.setText("Section : "+sectionModelList.get(position).getSectionName());
        holder.IsActive.setChecked(sectionModelList.get(position).isActive());
        holder.IsDeleted.setChecked(sectionModelList.get(position).isDeleted());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.registerSection(sectionModelList.get(position).getID(),sectionModelList.get(position).getSectionName(),
                        String.valueOf(holder.IsActive.isChecked()),  holder.IsDeleted.isChecked()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return sectionModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ID,SectionName;
        CheckBox IsActive,IsDeleted;
        Button update;
        public MyViewHolder(View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.ID);
            SectionName=itemView.findViewById(R.id.SectionName);
            IsActive=itemView.findViewById(R.id.IsActive);
            IsDeleted=itemView.findViewById(R.id.IsDeleted);
            update=itemView.findViewById(R.id.update);
        }
    }
}
