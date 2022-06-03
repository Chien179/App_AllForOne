package com.android.app_allforone.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.app_allforone.R;
import com.android.app_allforone.models.Movie;
import com.android.app_allforone.utils.DownloadImageTask;

import java.util.List;

public class SliderPagerAdapter extends PagerAdapter {
    private final Context context;
    private final List<Movie> movies;
    private final MovieItemClickListener movieItemClickListener;

    public SliderPagerAdapter(Context context, List<Movie> movies, MovieItemClickListener movieItemClickListener) {
        this.context = context;
        this.movies = movies;
        this.movieItemClickListener = movieItemClickListener;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View slideLayout = inflater.inflate(R.layout.slide_item, container, false);

        ImageView slideImg;
        TextView slideText;

        slideImg = slideLayout.findViewById(R.id.slide_img);
        slideText = slideLayout.findViewById(R.id.slide_title);

        new DownloadImageTask(slideImg).execute(movies.get(position).getCoverImage());
        slideText.setText(movies.get(position).getName());

        slideLayout.setOnClickListener(view -> movieItemClickListener.onMovieItemClick(movies.get(position), slideImg));

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
