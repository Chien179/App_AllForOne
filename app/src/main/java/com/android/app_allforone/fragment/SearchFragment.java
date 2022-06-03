package com.android.app_allforone.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_allforone.R;
import com.android.app_allforone.adapters.MovieAdapter;
import com.android.app_allforone.adapters.MovieItemClickListener;
import com.android.app_allforone.models.Movie;
import com.android.app_allforone.ui.MovieDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class SearchFragment extends Fragment implements MovieItemClickListener {
    MovieAdapter categoryAdapter;
    DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("movies");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        final EditText searchText = (EditText) view.findViewById(R.id.search_input);

        //Recyclerview Setup
        RecyclerView rv_movie_search = view.findViewById(R.id.rv_searched_movie);

        ArrayList<Movie> movies = getAllMovies();
        categoryAdapter = new MovieAdapter(getActivity(), movies, SearchFragment.this);
        rv_movie_search.setAdapter(categoryAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
        rv_movie_search.setLayoutManager(layoutManager);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList[] lstMovie = new ArrayList[]{new ArrayList<>()};
                lstMovie[0] = getMovies(searchText.getText().toString());

                categoryAdapter = new MovieAdapter(getActivity(), lstMovie[0], SearchFragment.this);
                rv_movie_search.setAdapter(categoryAdapter);
                GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2, RecyclerView.VERTICAL, false);
                rv_movie_search.setLayoutManager(layoutManager);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }

    private ArrayList<Movie> getMovies(String searchKey){
        ArrayList<Movie> movies = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot movieSnapshot:snapshot.getChildren()){
                    Movie movie = (Movie) movieSnapshot.getValue(Movie.class);

                    assert movie != null;
                    boolean containName = movie.getName().toLowerCase(Locale.ROOT).contains(searchKey.toLowerCase(Locale.ROOT));
                    boolean containNation = movie.getNation().toLowerCase(Locale.ROOT).contains(searchKey.toLowerCase(Locale.ROOT));
                    boolean containCate = movie.getCategory().contains(searchKey);
                    boolean containRelease = movie.getRelease().toLowerCase(Locale.ROOT).contains(searchKey.toLowerCase(Locale.ROOT));

                    if(containName || containNation || containCate || containRelease) {
                        movies.add(movie);
                    }

                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return movies;
    }

    private ArrayList<Movie> getAllMovies()
    {
        ArrayList<Movie> movies = new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot movieSnapshot:snapshot.getChildren()){
                    Movie movie = (Movie) movieSnapshot.getValue(Movie.class);
                    movies.add(movie);
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return movies;
    }

    @Override
    public void onMovieItemClick(Movie movie, ImageView movieImageView) {
        //Here we send movie information to detail activity
        //also we ll create the transition animation between the two activity
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra("name", movie.getName());
        intent.putExtra("image", movie.getImage());
        intent.putExtra("coverImage", movie.getCoverImage());
        intent.putExtra("duration", movie.getDuration());
        intent.putExtra("nation", movie.getNation());
        intent.putExtra("desc", movie.getDesc());
        intent.putExtra("link", movie.getLink());
        intent.putExtra("release", movie.getRelease());

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), movieImageView, "sharedName");

        startActivity(intent, options.toBundle());
    }

    @Override
    public void onMoviesSliderClick(Movie movie, ImageView movieImageView) {

    }
}