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

import com.example.schoolapp.Fragments.ClassFragment;
import com.example.schoolapp.Model.ClassModel;
import com.example.schoolapp.R;

import java.util.List;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.MyViewHolder> {

    public interface UpdateClass{
        public void registerClass(String ID,String ClassName,String IsActive,String IsDeleted);
    }


    List<ClassModel> classModelList;
    Context context;
    UpdateClass update;
    public ClassAdapter(List<ClassModel> classModelList, Context context, UpdateClass update){
        this.classModelList=classModelList;
       this. context=context;
       this. update=update;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.customclass,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
      holder.ID.setText("ID : "+classModelList.get(position).getID());
      holder.ClassName.setText("Class : "+classModelList.get(position).getClassName());
      holder.IsActive.setChecked(classModelList.get(position).getIsActive());
      holder.IsDeleted.setChecked(classModelList.get(position).getIsDeleted());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.registerClass(classModelList.get(position).getID(),classModelList.get(position).getClassName(),
                        String.valueOf(holder.IsActive.isChecked()),  String.valueOf(holder.IsDeleted.isChecked()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return classModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ID,ClassName;
        CheckBox IsActive,IsDeleted;
        Button update;
        public MyViewHolder(View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.ID);
            ClassName=itemView.findViewById(R.id.ClassName);
            IsActive=itemView.findViewById(R.id.IsActive);
            IsDeleted=itemView.findViewById(R.id.IsDeleted);
            update=itemView.findViewById(R.id.update);
        }
    }
}
