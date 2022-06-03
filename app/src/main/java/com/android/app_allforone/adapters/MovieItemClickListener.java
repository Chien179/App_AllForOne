package com.android.app_allforone.adapters;

import android.widget.ImageView;

import com.android.app_allforone.models.Movie;

public interface MovieItemClickListener {
    void onMovieItemClick(Movie movie, ImageView movieImageView);
    void onMoviesSliderClick(Movie movie, ImageView movieImageView);
}
