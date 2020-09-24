package com.example.schoolapp.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Model.StudentAttandantModel;
import com.example.schoolapp.R;

import java.util.List;

public class StudentAttandantAdapter extends RecyclerView.Adapter<StudentAttandantAdapter.MyViewHolder> {
   Activity activity;

    public StudentAttandantAdapter(Activity activity, List<StudentAttandantModel> studentAttandantModelList) {
        this.activity = activity;
        this.studentAttandantModelList = studentAttandantModelList;
    }

    List<StudentAttandantModel> studentAttandantModelList;

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customstudentattendance,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
     holder.name.setText(studentAttandantModelList.get(position).getName());
     holder.present.setChecked(studentAttandantModelList.get(position).isPresent());
     holder.absent.setChecked(studentAttandantModelList.get(position).isAbsent());
     holder.absent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             studentAttandantModelList.set(position,new StudentAttandantModel(studentAttandantModelList.get(position).getName(),false,true));
         }
     });

     holder.present.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
         @Override
         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             studentAttandantModelList.set(position,new StudentAttandantModel(studentAttandantModelList.get(position).getName(),true,false));
         }
     });
    }

    @Override
    public int getItemCount() {
        return studentAttandantModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        RadioButton present,absent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            present=itemView.findViewById(R.id.present);
            absent=itemView.findViewById(R.id.absent);
        }
    }
}
