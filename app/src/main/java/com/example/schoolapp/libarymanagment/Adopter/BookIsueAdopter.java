package com.example.schoolapp.libarymanagment.Adopter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.libarymanagment.modelclass.issueItem;

import java.util.List;

public class BookIsueAdopter extends RecyclerView.Adapter<BookIsueAdopter.MyViewHolder> {
   private FragmentActivity activity;
   private List<issueItem> bookLIstItems;
    public BookIsueAdopter(FragmentActivity activity, List<issueItem> bookLIstItems) {
        this.activity=activity;
        this.bookLIstItems=bookLIstItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.issuebookitem,parent,false);
        return new BookIsueAdopter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
holder.BookId.setText(bookLIstItems.get(position).getBookId());
holder.StudentId.setText(bookLIstItems.get(position).getStudentId());
holder.issueedDate.setText(bookLIstItems.get(position).getIssueedDate());
holder.Issuedforday.setText(bookLIstItems.get(position).getIssuedforday());
holder.SubmissionDate.setText(bookLIstItems.get(position).getSubmissionDate());
holder.IssuedBy.setText(bookLIstItems.get(position).getIssuedBy());
    }

    @Override
    public int getItemCount() {
        return bookLIstItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView StudentId,BookId,issueedDate,Issuedforday,SubmissionDate,IssuedBy,Remarks;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            StudentId=itemView.findViewById(R.id.StudentId);
                    BookId=itemView.findViewById(R.id.BookId);
            issueedDate=itemView.findViewById(R.id.issueedDate);
                    Issuedforday=itemView.findViewById(R.id.Issuedforday);
            SubmissionDate=itemView.findViewById(R.id.SubmissionDate);
                    IssuedBy=itemView.findViewById(R.id.IssuedBy);
        }
    }
}
