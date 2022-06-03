package com.android.app_allforone.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.app_allforone.R;

public class StreamVideoActivity extends AppCompatActivity implements MediaPlayer.OnCompletionListener {
    VideoView videoView;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stream_video_activity);

        final String link = getIntent().getExtras().getString("link");

        videoView = (VideoView) findViewById(R.id.videoViewMain);
        mediaController = (MediaController) findViewById(R.id.mediaController);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        videoView.setOnCompletionListener(this);

        // video name should be in lower case alphabet.
        videoView.setVideoURI(Uri.parse(link));
        videoView.start();
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}