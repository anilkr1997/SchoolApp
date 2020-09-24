package com.example.schoolapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Model.SchoolDashModel;
import com.example.schoolapp.R;

import java.util.List;

public class SchoolDashboardAadapter extends RecyclerView.Adapter<SchoolDashboardAadapter.MyViewHolder> {
    List<SchoolDashModel> schoolDashModels;
    Context context;

   public SchoolDashboardAadapter(List<SchoolDashModel> schoolDashModels, Context context){
       this.schoolDashModels=schoolDashModels;
       this.context=context;
   }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.custumdash,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.imageView.setImageResource(schoolDashModels.get(position).getImage());
        holder.textView.setText(schoolDashModels.get(position).getSchooltitle());
    }

    @Override
    public int getItemCount() {
        return schoolDashModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       ImageView imageView;
       TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.dashimage);
            textView=itemView.findViewById(R.id.dashtitle);
        }
    }
}
