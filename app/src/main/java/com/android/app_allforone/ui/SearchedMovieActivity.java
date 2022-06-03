package com.android.app_allforone.ui;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.app_allforone.R;
import com.android.app_allforone.adapters.MovieItemClickListener;
import com.android.app_allforone.adapters.SearchedMovieAdapter;
import com.android.app_allforone.models.Movie;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class SearchedMovieActivity extends AppCompatActivity implements MovieItemClickListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //var for get movie view
    SearchedMovieAdapter searchedMovieAdapter;
    List<Movie> lstMovieShow = new ArrayList<>();
    String transferredData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_movie);

        //var from getting search value
        Intent intent = getIntent();
        transferredData = intent.getExtras().getString("searchValue");

        //Recyclerview Setup
        //initiate var
        RecyclerView rv_searched_movie = findViewById(R.id.rv_searched_movie);

        //Change title
        TextView title = findViewById(R.id.search_title);
        title.setText(transferredData);

        //Get data up from firebase and start searching for category
//        getAllMovies();
        searchedMovieAdapter = new SearchedMovieAdapter(this, lstMovieShow, this);
        rv_searched_movie.setAdapter(searchedMovieAdapter);
        rv_searched_movie.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onMovieItemClick(Movie movie, ImageView movieImageView) {
        //Here we send movie information to detail activity
        //also we ll create the transition animation between the two activity
//        Log.d("movie cover photo: ", movie.getCoverPhoto());
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("title", movie.getName());
        intent.putExtra("thumbnail", movie.getImage());
        intent.putExtra("coverPhoto", movie.getCoverImage());
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, movieImageView, "sharedName");

        startActivity(intent, options.toBundle());

//        Toast.makeText(getActivity(), "item clicked" + movie.getTitle(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onMoviesSliderClick(Movie movie, ImageView movieImageView) {

    }

//    private void getAllMovies()
//    {
//        //Get reference for the Movie node
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("Movie");
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                Movie movie = snapshot.getValue(Movie.class);
//                assert movie != null;
//                if(Objects.equals(movie.getCategory(), transferredData))
//                    lstMovieShow.add(movie);
//                searchedMovieAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    //Back button
    public void back(View view){
        super.finish();
    }
}