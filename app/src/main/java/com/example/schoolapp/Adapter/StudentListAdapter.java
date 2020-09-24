package com.example.schoolapp.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.schoolapp.GenerateIdCard;
import com.example.schoolapp.Model.StudentListModel;
import com.example.schoolapp.R;
import com.example.schoolapp.StudentAdmission;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<StudentListModel> studentLists;
    List<StudentListModel> filterstudentLists;

    public StudentListAdapter(Context context, List<StudentListModel> studentLists) {
        this.context = context;
        this.studentLists = studentLists;
        this.filterstudentLists = studentLists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.customstudentlist,null,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
    holder.Class.setText(filterstudentLists.get(position).getClasss());
    holder.name.setText(filterstudentLists.get(position).getName());
    holder.admission.setText(filterstudentLists.get(position).getAdmission());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.images)
                .error(R.drawable.images)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.LOW);

        Glide.with(context).load("http://ratnakar-001-site1.atempurl.com/Studentimage/"+filterstudentLists.get(position).getImage())
                .apply(options)
                .into(holder.image);
    holder.lin.setOnClickListener(
            new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //context.startActivity(new Intent(context, StudentAdmission.class).putExtra("ID",filterstudentLists.get(position).getID()));
            final Dialog dialog=new Dialog(context);
          dialog.setContentView(R.layout.dailogstudent);
          dialog.setCancelable(true);
          TextView update,generate;
            update=dialog.findViewById(R.id.update);
            generate=dialog.findViewById(R.id.generate);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, StudentAdmission.class).putExtra("ID",filterstudentLists.get(position).getID()));
                     dialog.dismiss();
                }
            });
            generate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, GenerateIdCard.class).putExtra("ID",filterstudentLists.get(position).getID()));

                    dialog.dismiss();
                }
            });

            dialog.show();
        }
    });
    }

    @Override
    public int getItemCount() {
        return filterstudentLists.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filterstudentLists = studentLists;
                }

                else {
                    List<StudentListModel> filteredList = new ArrayList<>();
                    for (StudentListModel row : studentLists) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterstudentLists = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterstudentLists;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterstudentLists = (ArrayList<StudentListModel>) results.values;
                notifyDataSetChanged();
                int amount=0;
                for(int i=0;i<filterstudentLists.size();i++){
                //    amount=amount+Integer.parseInt(filterstudentLists.get(i).getStatus());
                }
            //    Toast.makeText(activity,amount+"",0).show();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView Class,name,admission;
        LinearLayout lin;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            Class=itemView.findViewById(R.id.Class);
            name=itemView.findViewById(R.id.name);
            admission=itemView.findViewById(R.id.admission);
            lin=itemView.findViewById(R.id.lin);
        }
    }
}
