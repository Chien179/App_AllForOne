package com.android.app_allforone.adapters;

import android.widget.ImageView;

import com.android.app_allforone.models.Movie;

public interface MovieItemClickListener {
    void onMovieClick(Movie movie, ImageView movieImageView); //We will need the ImageView to make the shared animation between the two activity
}
