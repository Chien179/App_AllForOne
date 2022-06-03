package com.android.app_allforone.ui;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.app_allforone.R;
import com.android.app_allforone.adapters.MoreMovieAdapter;
import com.android.app_allforone.models.Movie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StreamVideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    VideoView videoView;
    MediaController mediaController;
    TextView txtTitle;
    ListView lvMovies;
    DatabaseReference databaseReference;
    MoreMovieAdapter moreMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stream_video_activity);

        databaseReference = FirebaseDatabase.getInstance().getReference("movies");

        final String name = getIntent().getExtras().getString("name");
        final String release = getIntent().getExtras().getString("release");
        final String link = getIntent().getExtras().getString("link");

        txtTitle = (TextView) findViewById(R.id.textViewTitle);
        lvMovies = (ListView) findViewById(R.id.lvMovies);
        videoView = (VideoView) findViewById(R.id.videoViewMain);
        mediaController = (MediaController) findViewById(R.id.mediaController);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setOnCompletionListener(this);

        txtTitle.setText(name + " (" + release + ")");



        // video name should be in lower case alphabet.
        videoView.setVideoURI(Uri.parse(link));
        videoView.start();

        ArrayList<Movie> movies = (ArrayList<Movie>) getAllMovies();

        moreMovieAdapter = new MoreMovieAdapter(StreamVideoActivity.this, R.layout.activity_more_movie, movies);

        lvMovies.setAdapter(moreMovieAdapter);
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
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
                    moreMovieAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return movies;
    }
}