package com.android.app_allforone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_allforone.R;
import com.android.app_allforone.models.Movie;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    Context context;
    List<Movie> mData;

    public CategoryAdapter(Context context, List<Movie> mData) {
        this.context = context;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, viewGroup, false);
        return new CategoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.MyViewHolder myViewHolder, int position) {
        myViewHolder.TvTitle.setText(mData.get(position).getTitle());
//        myViewHolder.ImgMovie.setImageResource(mData.getThumbnail());
//        mData.get(position).getThumbnail()
        //Uri uri = Uri.parse(mData.get(position).getThumbnail());
//        myViewHolder.ImgMovie.setImageURI(uri);
       // Glide.with(context)
                //.load(uri)
                //.into(myViewHolder.ImgMovie);
        myViewHolder.ImgMovie.setImageResource(Integer.parseInt(mData.get(position).getThumbnail()));

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView TvTitle;
        private ImageView ImgMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TvTitle = itemView.findViewById(R.id.movie_item_title);
            ImgMovie = itemView.findViewById(R.id.movie_item_img);
        }
    }
}
