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

import com.example.schoolapp.Model.AddStaffModel;
import com.example.schoolapp.R;

import java.util.List;

public class AddStaffAdapter extends RecyclerView.Adapter<AddStaffAdapter.MyViewHolder> {
    List<AddStaffModel> addStaffModelList;
    Context context;
    updateStaff updateStaff;

   public interface  updateStaff{
        public void UpdateStaff(AddStaffModel addStaffModel);
    }

    public AddStaffAdapter(List<AddStaffModel> addStaffModelList, Context context, AddStaffAdapter.updateStaff updateStaff) {
        this.addStaffModelList = addStaffModelList;
        this.context = context;
        this.updateStaff = updateStaff;
    }




    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customclass,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
          holder.ID.setText(addStaffModelList.get(position).getID());
          holder.Staffprofile.setText(addStaffModelList.get(position).getStaffProfile());
          holder.IsActive.setChecked(Boolean.parseBoolean(addStaffModelList.get(position).getIsActive()));
          holder.IsDeleted.setChecked(Boolean.parseBoolean(addStaffModelList.get(position).getIsDeleted()));
          holder.update.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  updateStaff.UpdateStaff(addStaffModelList.get(position));
              }
          });
    }



    @Override
    public int getItemCount() {

        return addStaffModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ID,Staffprofile;
        CheckBox IsActive,IsDeleted;
        Button update;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.ID);
            Staffprofile=itemView.findViewById(R.id.ClassName);
            IsActive=itemView.findViewById(R.id.IsActive);
            IsDeleted=itemView.findViewById(R.id.IsDeleted);
            update=itemView.findViewById(R.id.update);
        }
    }
}
