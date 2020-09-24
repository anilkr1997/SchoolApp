package com.example.schoolapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
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
import com.example.schoolapp.Model.StaffListModel;
import com.example.schoolapp.Model.StudentListModel;
import com.example.schoolapp.R;
import com.example.schoolapp.StudentAdmission;

import java.util.ArrayList;
import java.util.List;
//227605901
//229181130
public class StaffListAdapter extends RecyclerView.Adapter<StaffListAdapter.MyViewHolder> implements Filterable {
    Context context;
    List<StaffListModel> staffListModelList;
    List<StaffListModel> filterstaffListModelList;
    SHowUpadate sHowUpadate;

    public interface SHowUpadate{
       public void showupdate(String id,String name,String father,String mother ,String Add,String Phone,
                              String email, String dob, String registerdate,String profile,String imagepath);
    }
    public StaffListAdapter(Context context, List<StaffListModel> staffListModelList,  SHowUpadate sHowUpadate) {
        this.context = context;
        this.staffListModelList = staffListModelList;
        this.filterstaffListModelList = staffListModelList;
        this.sHowUpadate = sHowUpadate;
    }



    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customstudentlist,parent,false);

        return new MyViewHolder(view);
    }
//studentimage
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.Class.setText(filterstaffListModelList.get(position).getPhone());
        holder.name.setText(filterstaffListModelList.get(position).getEmpName());
        holder.admission.setText(filterstaffListModelList.get(position).getEmpTypeId());
        try {
            Glide.with(context).load( "http://ratnakar-001-site1.atempurl.com/empimage/"+filterstaffListModelList.get(position).getImagePath()).into(  holder.image);

          //  holder.image.setImageBitmap(convertBase64ToBitmap(filterstaffListModelList.get(position).getImagebase4()));
        }
        catch (Exception e){}
        holder.lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sHowUpadate.showupdate(filterstaffListModelList.get(position).getId(),filterstaffListModelList.get(position).getEmpName()
                        ,filterstaffListModelList.get(position).getFatherName(),filterstaffListModelList.get(position).getMotherName()
                        ,filterstaffListModelList.get(position).getAddress(),filterstaffListModelList.get(position).getPhone()
                        ,filterstaffListModelList.get(position).getEmail(),filterstaffListModelList.get(position).getDOJ()
                        ,filterstaffListModelList.get(position).getCreatedDate() ,filterstaffListModelList.get(position).getEmpTypeId()
                        ,filterstaffListModelList.get(position).getImagePath());
               // context.startActivity(new Intent(context, StudentAdmission.class).putExtra("ID",filterstaffListModelList.get(position).getId()));

            }
        });
    }

    @Override
    public int getItemCount() {
        return filterstaffListModelList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    filterstaffListModelList = staffListModelList;
                }

                else {
                    List<StaffListModel> filteredList = new ArrayList<>();
                    for (StaffListModel row : staffListModelList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getEmpName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    filterstaffListModelList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filterstaffListModelList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterstaffListModelList = (ArrayList<StaffListModel>) results.values;
                notifyDataSetChanged();
                int amount=0;
                for(int i=0;i<filterstaffListModelList.size();i++){
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
        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.image);
            Class=itemView.findViewById(R.id.Class);
            name=itemView.findViewById(R.id.name);
            admission=itemView.findViewById(R.id.admission);
            lin=itemView.findViewById(R.id.lin);

        }
    }
    private Bitmap convertBase64ToBitmap(String b64) {
        byte[] imageAsBytes = Base64.decode(b64.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }
}
