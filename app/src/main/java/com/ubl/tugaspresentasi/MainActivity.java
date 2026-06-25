package com.ubl.tugaspresentasi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MediaPlayer mediaPlayer;
    private TextView textStatus;
    private VideoView videoBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textStatus = findViewById(R.id.textStatus);
        videoBackground = findViewById(R.id.videoBackground);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnStop = findViewById(R.id.btnStop);
        Button btnGoToLyrics = findViewById(R.id.btnGoToLyrics);

        // Inisialisasi awal MediaPlayer
        setupMediaPlayer();

        // Setup Background Video (Looping)
        setupBackgroundVideo();

        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.start();
                videoBackground.start(); // Mulai video saat lagu diputar
                textStatus.setText(R.string.status_playing);
            }
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                videoBackground.pause(); // Pause video saat lagu dipause
                textStatus.setText(R.string.status_paused);
            }
        });

        btnStop.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                videoBackground.stopPlayback(); // Stop video
                textStatus.setText(R.string.status_stopped);
                setupMediaPlayer();
                setupBackgroundVideo(); // Reset video
            }
        });

        btnGoToLyrics.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LyricsActivity.class);
            startActivity(intent);
        });
    }

    private void setupMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        try {
            mediaPlayer = MediaPlayer.create(this, R.raw.song);
        } catch (Exception e) {
            Log.e(TAG, "Gagal inisialisasi MediaPlayer. Pastikan ada res/raw/song.mp3", e);
        }
    }

    private void setupBackgroundVideo() {
        try {
            // Menggunakan getIdentifier agar tidak error merah jika file background.mp4 belum ada
            int resId = getResources().getIdentifier("background", "raw", getPackageName());
            if (resId != 0) {
                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + resId);
                videoBackground.setVideoURI(videoUri);
                videoBackground.setOnPreparedListener(mp -> mp.setLooping(true));
            } else {
                Log.w(TAG, "Video background.mp4 tidak ditemukan di res/raw");
            }
        } catch (Exception e) {
            Log.e(TAG, "Gagal memuat video background", e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
