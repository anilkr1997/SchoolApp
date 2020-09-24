package com.example.schoolapp.libarymanagment.Adopter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolapp.R;
import com.example.schoolapp.libarymanagment.modelclass.bookLIstItem;

import java.util.List;

public class BooklistAdopter extends RecyclerView.Adapter<BooklistAdopter.MyViewHolder> {
  private FragmentActivity activity;
   private List<bookLIstItem> bookLIstItems;
    public BooklistAdopter(FragmentActivity activity, List<bookLIstItem> bookLIstItems) {
        this.activity=activity;
        this.bookLIstItems=bookLIstItems;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.booklist,parent,false);
        return new BooklistAdopter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
holder.b_name.setText(bookLIstItems.get(position).getBookName());
holder.B_code.setText(bookLIstItems.get(position).getBookCode());
holder.Subject_Code.setText(bookLIstItems.get(position).getSubjectCode());
holder.R_number.setText(bookLIstItems.get(position).getRacNo());
holder.Auther_name.setText(bookLIstItems.get(position).getAuthorName());
holder.B_Quantity.setText(bookLIstItems.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return bookLIstItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView b_name,B_code,Subject_Code,R_number,Auther_name,B_Quantity;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            b_name=itemView.findViewById(R.id.BookName);
            B_code=itemView.findViewById(R.id.BookCode);
            Subject_Code=itemView.findViewById(R.id.SubjectCode);
            R_number=itemView.findViewById(R.id.RacNo);
            Auther_name=itemView.findViewById(R.id.AuthorName);
            B_Quantity=itemView.findViewById(R.id.Quantity);
        }
    }
}
