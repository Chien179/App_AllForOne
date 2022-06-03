package com.android.app_allforone.fragment;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.app_allforone.R;
import com.android.app_allforone.adapters.MovieAdapter;
import com.android.app_allforone.adapters.MovieItemClickListener;
import com.android.app_allforone.adapters.SliderPagerAdapter;
import com.android.app_allforone.models.Movie;
import com.android.app_allforone.ui.MovieDetailActivity;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageFragment extends Fragment implements MovieItemClickListener {

    private ViewPager sliderpaper;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    MovieAdapter movieAdapter;
    SliderPagerAdapter adapter;
    ArrayList<Movie> listslide;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = FirebaseDatabase.getInstance().getReference("movies");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
        sliderpaper = view.findViewById(R.id.silder_paper);
        listslide = (ArrayList<Movie>) getAllMovies();
        adapter = new SliderPagerAdapter(getActivity(), listslide, HomePageFragment.this) ;
        sliderpaper.setAdapter(adapter);

        //Set up time for changing the theme
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 2000, 4000);
        TabLayout indicator = view.findViewById(R.id.indicator);
        indicator.setupWithViewPager(sliderpaper, true);

        //Recyclerview Setup
        RecyclerView movieRV = view.findViewById((R.id.rv_movie));

        ArrayList<Movie> movies = (ArrayList<Movie>) getAllMovies();
        movieAdapter = new MovieAdapter(getActivity(), movies, HomePageFragment.this);
        movieRV.setAdapter(movieAdapter);
        movieRV.setLayoutManager(new GridLayoutManager(getActivity(), 2){
            @Override
            public boolean canScrollVertically() {
                return false;
            }

            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        });

        // Inflate the layout for this fragment
        return view;
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

    class SliderTimer extends TimerTask {

        @Override
        public void run() {
            if(isAdded()){
                getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        if(sliderpaper.getCurrentItem() < (listslide.size() - 1)){
                            sliderpaper.setCurrentItem(sliderpaper.getCurrentItem()+1);
                        }
                        else
                            sliderpaper.setCurrentItem(0);
                    }
                });
            }
        }
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
                    movieAdapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return movies;
    }


}