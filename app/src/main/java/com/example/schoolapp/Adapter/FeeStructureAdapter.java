package com.example.schoolapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.Model.FeeStructureModel;
import com.example.schoolapp.R;

import java.util.List;

public class FeeStructureAdapter extends RecyclerView.Adapter<FeeStructureAdapter.MyViewHolder> {
    UpadatStructure upadatStructure;
    Context context;

    public FeeStructureAdapter(UpadatStructure upadatStructure, Context context, List<FeeStructureModel> feeStructureModelList) {
        this.upadatStructure = upadatStructure;
        this.context = context;
        this.feeStructureModelList = feeStructureModelList;
    }

    List<FeeStructureModel> feeStructureModelList;
   public interface UpadatStructure{
        public void UpdateFee(final String ID, final String ClassId, final String SessionId
                , final String Anually
                , final String Course_Monthly
                , final String Quaterly, final String IsActive, final String IsDeleted);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.customfeestructure,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
           holder.ID.setText(feeStructureModelList.get(position).getID());
           holder.ClassId.setText(feeStructureModelList.get(position).getClassId());
           holder.FeeTypeID.setText(feeStructureModelList.get(position).getFeeTypeID());
           holder.SessionId.setText(feeStructureModelList.get(position).getSessionId());
           holder.Anually.setText(feeStructureModelList.get(position).getAnually());
           holder.Course_Monthly.setText(feeStructureModelList.get(position).getCourse_Monthly());
           holder.Quaterly.setText(feeStructureModelList.get(position).getQuaterly());
           holder.update.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   upadatStructure.UpdateFee(
                           feeStructureModelList.get(position).getID(),
                           feeStructureModelList.get(position).getClassId(),
                           feeStructureModelList.get(position).getSessionId(),
                           feeStructureModelList.get(position).getAnually(),
                           feeStructureModelList.get(position).getCourse_Monthly(),
                           feeStructureModelList.get(position).getQuaterly(),
                           "true","false"

                           );
               }
           });

    }

    @Override
    public int getItemCount() {
        return feeStructureModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ID,ClassId,FeeTypeID,SessionId,Anually,Course_Monthly,Quaterly;
        Button update;
        public MyViewHolder(View itemView) {
            super(itemView);
            ID=itemView.findViewById(R.id.ID);
            ClassId=itemView.findViewById(R.id.ClassId);
            FeeTypeID=itemView.findViewById(R.id.FeeTypeID);
            SessionId=itemView.findViewById(R.id.SessionId);
            Anually=itemView.findViewById(R.id.Anually);
            Course_Monthly=itemView.findViewById(R.id.Course_Monthly);
            Quaterly=itemView.findViewById(R.id.Quaterly);
            update=itemView.findViewById(R.id.update);
        }
    }
}
