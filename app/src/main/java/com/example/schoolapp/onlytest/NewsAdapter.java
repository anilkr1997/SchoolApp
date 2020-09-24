package com.example.schoolapp.onlytest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.schoolapp.R;
import com.example.schoolapp.StudentAdmission;

import java.util.List;

public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {
    List<NewsModel> newsModelList;
    Context context;

    public NewsAdapter(List<NewsModel> newsModelList, Context context) {
        this.newsModelList = newsModelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customgallery,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       holder.title.setText(newsModelList.get(position).getTitle());
       holder.description.setText(newsModelList.get(position).getDescription());
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_photo_camera_black_24dp)
                .error(R.drawable.ic_photo_camera_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .priority(Priority.LOW);

        Glide.with(context).load(newsModelList.get(position).getImagepath())
                .apply(options)
                .into(holder.image);
        Log.d("ashishsikarwar",newsModelList.get(0).getImagepath());

       // image
    }

    @Override
    public int getItemCount() {
        return newsModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title,description;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
            image=itemView.findViewById(R.id.image);
        }
    }
}
