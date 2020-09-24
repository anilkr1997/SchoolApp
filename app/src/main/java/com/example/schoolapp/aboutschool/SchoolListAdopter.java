package com.example.schoolapp.aboutschool;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;

import java.util.List;

public class SchoolListAdopter extends RecyclerView.Adapter<SchoolListAdopter.MyViewHolder> {
   private FragmentActivity activity;
   private List<Schoolmodelclass> schoolmodelclasses;

    public SchoolListAdopter(FragmentActivity activity, List<Schoolmodelclass> schoolmodelclasses) {
        this.activity=activity;
        this.schoolmodelclasses=schoolmodelclasses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.schoollistitem,null,false);
        return new SchoolListAdopter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.schoolid.setText(schoolmodelclasses.get(position).getID());
        holder.schoolname.setText(schoolmodelclasses.get(position).getSchoolName());
        holder.schoolprincplename.setText(schoolmodelclasses.get(position).getPrincipalName());
        holder.schoolurl.setText(schoolmodelclasses.get(position).getWebsiteUrl());
        holder.schoolmail.setText(schoolmodelclasses.get(position).getEmail());
        holder.schoolnumber.setText(schoolmodelclasses.get(position).getPhoneNo());
        holder.schoolwonername.setText(schoolmodelclasses.get(position).getOwnerName());
        holder.SchoolAddress.setText(schoolmodelclasses.get(position).getAddress());
        holder.Boardid.setText(schoolmodelclasses.get(position).getBoardID());
        holder.SchoolAbout.setText(schoolmodelclasses.get(position).getAboutSchool());
    }

    @Override
    public int getItemCount() {
        return schoolmodelclasses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView schoolname,schoolid,schoolprincplename,schoolurl,schoolmail,schoolnumber,schoolwonername,SchoolAddress,
                Boardid,SchoolAbout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            schoolname=itemView.findViewById(R.id.Schoolname);
            schoolid=itemView.findViewById(R.id.Schoolid);
            schoolprincplename=itemView.findViewById(R.id.SchoolPrincpalname);
            schoolurl=itemView.findViewById(R.id.Schoolwebsiteurl);
            schoolmail=itemView.findViewById(R.id.SchoolEmail);
            schoolnumber=itemView.findViewById(R.id.SchoolPhonembur);
            schoolwonername=itemView.findViewById(R.id.Schoolwonername);
            SchoolAddress=itemView.findViewById(R.id.Schooladress);
            Boardid=itemView.findViewById(R.id.SchoolBrodid);
            SchoolAbout=itemView.findViewById(R.id.AboutSchool);

        }
    }
}
