package com.example.schoolapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
//44983f72d3624288a1add8261231915e apikey
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Model.TimeTableModel;
import com.example.schoolapp.R;

import java.util.List;

public class TimeTableAdapter extends RecyclerView.Adapter<TimeTableAdapter.MyViewHolder> {
    public interface UpdateTime{
        public void updateTime(String id, String sub,String starttime,String tilltime,String teacher);
    }

    UpdateTime updateTime;
    List<TimeTableModel> timeTableModelList;
    Context context;

    public TimeTableAdapter(UpdateTime updateTime, List<TimeTableModel> timeTableModelList, Context context) {
        this.updateTime = updateTime;
        this.timeTableModelList = timeTableModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customtimetable,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
         holder.subject.setText(timeTableModelList.get(position).getSubject());
         holder.starttime.setText(timeTableModelList.get(position).getStarttime());
         holder.tilltime.setText(timeTableModelList.get(position).getEndtime());
         holder.teacher.setText(timeTableModelList.get(position).getTeachername());
         //timeTableModelList.get(position).getId()
         holder.arrow.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 updateTime.updateTime(position+"",timeTableModelList.get(position).getSubject(),
                         timeTableModelList.get(position).getStarttime(),timeTableModelList.get(position).getEndtime(),
                         timeTableModelList.get(position).getTeachername());
             }
         });
    }

    @Override
    public int getItemCount() {
        return timeTableModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView subject,starttime,tilltime,teacher;
        LinearLayout lin;
        Button arrow;
        public MyViewHolder(View itemView) {
            super(itemView);
            lin=itemView.findViewById(R.id.lin);
            subject=itemView.findViewById(R.id.subject);
            starttime=itemView.findViewById(R.id.starttime);
            tilltime=itemView.findViewById(R.id.tilltime);
            teacher=itemView.findViewById(R.id.teacher);
            arrow=itemView.findViewById(R.id.arrow);
        }
    }
}
