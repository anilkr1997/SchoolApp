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
import com.example.schoolapp.Model.SessionModel;
import com.example.schoolapp.R;

import java.util.List;

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.MyViewHolder> {
    public interface UpdateSession{
        public void registerSession(String ID,String SessionName,String IsActive,String IsDeleted);
    }

    List<SessionModel> sessionModelList;
    Context context;
    UpdateSession update;

    public SessionAdapter(List<SessionModel> sessionModelList, Context context, UpdateSession update) {
        this.sessionModelList = sessionModelList;
        this.context = context;
        this.update = update;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.customclass,null,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.ID.setText("ID : "+sessionModelList.get(position).getId());
        holder.ClassName.setText("Session : "+sessionModelList.get(position).getSessionName());
        holder.IsActive.setChecked(Boolean.parseBoolean(sessionModelList.get(position).getIsActive()));
        holder.IsDeleted.setChecked(Boolean.parseBoolean(sessionModelList.get(position).getIsDeleted()));
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update.registerSession(sessionModelList.get(position).getId(),sessionModelList.get(position).getSessionName(),
                        String.valueOf(holder.IsActive.isChecked()),  String.valueOf(holder.IsDeleted.isChecked()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ID,ClassName;
        CheckBox IsActive,IsDeleted;
        Button update;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.ID);
            ClassName=itemView.findViewById(R.id.ClassName);
            IsActive=itemView.findViewById(R.id.IsActive);
            IsDeleted=itemView.findViewById(R.id.IsDeleted);
            update=itemView.findViewById(R.id.update);
        }
    }
}
