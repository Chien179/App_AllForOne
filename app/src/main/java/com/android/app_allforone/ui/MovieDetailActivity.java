package com.android.app_allforone.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.app_allforone.R;
import com.android.app_allforone.utils.DownloadImageTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MovieDetailActivity extends AppCompatActivity {
    private FloatingActionButton floatBtnPlay;
    private ImageView imageMovie;
    private ImageView coverImageMovie;
    private TextView txtTitle;
    private TextView txtNation;
    private TextView txtDuration;
    private TextView txtRelease;
    private TextView txtDesc;
    private Button btnPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        addView();
        addListener();
    }

    private void addView(){
        final String name = getIntent().getExtras().getString("name");
        final String coverImage = getIntent().getExtras().getString("coverImage");
        final String image = getIntent().getExtras().getString("image");
        final String desc = getIntent().getExtras().getString("desc");
        final int release = getIntent().getExtras().getInt("release");
        final int duration = getIntent().getExtras().getInt("duration");
        final String nation = getIntent().getExtras().getString("nation");

        //initiate views
        floatBtnPlay = findViewById(R.id.play_fab);
        imageMovie = findViewById(R.id.image);
        new DownloadImageTask(imageMovie).execute(image);
        coverImageMovie = findViewById(R.id.coverImage);
        new DownloadImageTask(coverImageMovie).execute(coverImage);
        txtTitle = findViewById(R.id.name);
        txtDesc = findViewById(R.id.desc);
        btnPlay = findViewById(R.id.btnPlay);
        txtDuration = findViewById(R.id.duration);
        txtNation = findViewById(R.id.nation);
        txtRelease = findViewById(R.id.release);

        txtTitle.setText(name);
        txtDuration.setText(Integer.toString(duration) + " Phút");
        txtNation.setText(nation);
        txtRelease.setText(Integer.toString(release));
        txtDesc.setText(desc);

        //setup animation
        floatBtnPlay.setAnimation(AnimationUtils.loadAnimation(this, R.anim.scale_animation));
    }

    private void addListener(){
        btnPlay.setOnClickListener(this::stream);
        floatBtnPlay.setOnClickListener(this::stream);
    }

    public void stream(View view)
    {
        final String name = getIntent().getExtras().getString("name");
        final String release = getIntent().getExtras().getString("release");
        final String link = getIntent().getExtras().getString("link");

        Intent intent = new Intent(MovieDetailActivity.this, StreamVideoActivity.class);
        intent.putExtra("link", link);
        intent.putExtra("name", name);
        intent.putExtra("release", release);

        startActivity(intent);
    }
}