package com.example.schoolapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.schoolapp.Model.GalleryAchieve;
import com.example.schoolapp.R;

import java.util.List;

public class GallaryAdapter extends RecyclerView.Adapter<GallaryAdapter.MyViewHolder> {

    public interface updategallery{
        public void upadateGallery(String ID,String title,String description,String imagename);
    }
    Context context;
    List<GalleryAchieve> galleryAchieveList;
    updategallery updategallery;

    public GallaryAdapter(Context context, List<GalleryAchieve> galleryAchieveList, GallaryAdapter.updategallery updategallery) {
        this.context = context;
        this.galleryAchieveList = galleryAchieveList;
        this.updategallery = updategallery;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customgallery,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
      holder.title.setText(galleryAchieveList.get(position).getTittle());
      holder.description.setText(galleryAchieveList.get(position).getDescription());
      holder.title.setText(galleryAchieveList.get(position).getTittle());
      holder.edit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
          updategallery.upadateGallery(galleryAchieveList.get(position).getID(),galleryAchieveList.get(position).getTittle()
          ,galleryAchieveList.get(position).getDescription(),galleryAchieveList.get(position).getFileName());
          }
      });
        try {
            RequestOptions options = new RequestOptions()
                  
                    .placeholder(R.drawable.ic_broken_image_black_24dp)
                    .error(R.drawable.ic_broken_image_black_24dp)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .priority(Priority.LOW);

            Glide.with(context).load("http://ratnakar-001-site1.atempurl.com/GallaryAchivement/"+galleryAchieveList.get(position).getFileName())
                    .apply(options)
                    .into(holder.image);



          //  Glide.with(context).load( "http://ratnakar-001-site1.atempurl.com/GallaryAchivement/"+galleryAchieveList.get(position).getFileName()).into(  holder.image);

            //  holder.image.setImageBitmap(convertBase64ToBitmap(filterstaffListModelList.get(position).getImagebase4()));
        }

        catch (Exception e){}
    }

    @Override
    public int getItemCount() {
        return galleryAchieveList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView description,title;
        Button edit;
        ImageView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            description=itemView.findViewById(R.id.description);
            title=itemView.findViewById(R.id.title);
            edit=itemView.findViewById(R.id.edit);
            image=itemView.findViewById(R.id.image);

        }
    }
}
