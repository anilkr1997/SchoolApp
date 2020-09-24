package com.example.schoolapp.Transport.Adopter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.Transport.modelclass.VechiltypeModelclass;

import java.util.List;

public class vechileTypeAdopter extends RecyclerView.Adapter<vechileTypeAdopter.MyViewHolder> {
    public interface UpdateVcheltype{
        public void registerClass(String ID, String VehicalTypename, String IsActive, String IsDeleted);
    }

    private FragmentActivity activity;
    private List<VechiltypeModelclass> vechiltype;
   private UpdateVcheltype updateVcheltype;
    public vechileTypeAdopter(FragmentActivity activity, List<VechiltypeModelclass> vechiltype, UpdateVcheltype updateVcheltype) {
        this.activity=activity;
        this.vechiltype=vechiltype;
        this.updateVcheltype=updateVcheltype;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.vechiletypeitemlist,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.id.setText(vechiltype.get(position).getID());
        holder.vechelname.setText(vechiltype.get(position).getVehicalTypename());
        holder.IsActive.setChecked(vechiltype.get(position).getIsActive());
        holder.IsDeleted.setChecked(vechiltype.get(position).getIsDeleted());
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateVcheltype.registerClass(vechiltype.get(position).getID(),vechiltype.get(position).getVehicalTypename(),
                        String.valueOf(holder.IsActive.isChecked()),  String.valueOf(holder.IsDeleted.isChecked()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return vechiltype.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id,vechelname;
        CheckBox IsActive,IsDeleted;
        Button update;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            IsActive=itemView.findViewById(R.id.IsActive);
            IsDeleted=itemView.findViewById(R.id.IsDeleted);
            update=itemView.findViewById(R.id.update);
            id=itemView.findViewById(R.id.ID);
            vechelname=itemView.findViewById(R.id.ClassName);
        }
    }
}
