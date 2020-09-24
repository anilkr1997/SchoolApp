package com.example.schoolapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.schoolapp.Fragments.FeeCollectionModel;
import com.example.schoolapp.R;
import java.util.List;


public class FeeCollectionAdapter extends RecyclerView.Adapter<FeeCollectionAdapter.MyViewHolder> {

    public interface Sharefee{
        public void ShareFeeDetails(String id,String name,String father, String admissionno ,String classid,
                                    String  date,String month,String amount,String session,
                                    String submitby,String updatedby);
    }

    Sharefee sharefee;
    List<FeeCollectionModel> feeCollectionModelList;
    Context context;
    public FeeCollectionAdapter(Sharefee sharefee, List<FeeCollectionModel> feeCollectionModelList, Context context) {
        this.sharefee = sharefee;
        this.feeCollectionModelList = feeCollectionModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.customfeecollection,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.name.setText(feeCollectionModelList.get(position).getName());
        holder.father.setText(feeCollectionModelList.get(position).getFather());
        holder.admission.setText(feeCollectionModelList.get(position).getAdmissionNo());
        holder.Class.setText(feeCollectionModelList.get(position).getClassId());
        holder.date.setText(feeCollectionModelList.get(position).getSubmissionDate());
        holder.month.setText(feeCollectionModelList.get(position).getMonth());
        holder.amount.setText(feeCollectionModelList.get(position).getAmount());
        holder.session.setText(feeCollectionModelList.get(position).getSessionId());
        holder.submitby.setText(feeCollectionModelList.get(position).getSubmitedBy());
        holder.updatedby.setText(feeCollectionModelList.get(position).getUpdatedBy());
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharefee.ShareFeeDetails(feeCollectionModelList.get(position).getID(),
                        feeCollectionModelList.get(position).getName(),
                        feeCollectionModelList.get(position).getFather(),
                        feeCollectionModelList.get(position).getAdmissionNo(),
                        feeCollectionModelList.get(position).getClassId(),
                        feeCollectionModelList.get(position).getSubmissionDate(),
                        feeCollectionModelList.get(position).getMonth(),
                        feeCollectionModelList.get(position).getAmount(),
                        feeCollectionModelList.get(position).getSessionId(),
                        feeCollectionModelList.get(position).getSubmitedBy(),
                        feeCollectionModelList.get(position).getUpdatedBy());
            }
        });
    }


    @Override
    public int getItemCount() {
        return feeCollectionModelList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,Class,father,admission,date,month,amount,session,submitby,updatedby;
        Button share;
        public MyViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            Class=itemView.findViewById(R.id.Class);
            father=itemView.findViewById(R.id.father);
            admission=itemView.findViewById(R.id.admission);
            date=itemView.findViewById(R.id.date);
            month=itemView.findViewById(R.id.month);
            amount=itemView.findViewById(R.id.amount);
            session=itemView.findViewById(R.id.session);
            submitby=itemView.findViewById(R.id.submitby);
            updatedby=itemView.findViewById(R.id.updatedby);
            share=itemView.findViewById(R.id.share);

        }
    }
}
