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
import com.android.app_allforone.utils.DownloadImageTask;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    Context context;
    ArrayList<Movie> mData;

    MovieItemClickListener movieItemClickListener;

    public MovieAdapter(Context context, ArrayList<Movie> mData, MovieItemClickListener listener) {
        this.context = context;
        this.mData = mData;
        this.movieItemClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.txtTitle.setText(mData.get(position).getName());
        new DownloadImageTask(myViewHolder.ImgMovie).execute(mData.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView txtTitle;
        private final ImageView ImgMovie;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.movieItemTitle);
            ImgMovie = itemView.findViewById(R.id.movieItemImg);

            itemView.setOnClickListener(view -> movieItemClickListener.onMovieItemClick(mData.get(getAdapterPosition()), ImgMovie));
        }
    }
}
