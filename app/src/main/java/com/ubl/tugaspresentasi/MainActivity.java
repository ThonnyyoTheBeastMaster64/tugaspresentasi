package com.ubl.tugaspresentasi;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MediaPlayer mediaPlayer;
    private TextView textStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textStatus = findViewById(R.id.textStatus);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnPause = findViewById(R.id.btnPause);
        Button btnStop = findViewById(R.id.btnStop);

        // Menghubungkan ke file mp3 di folder res/raw/song.mp3
        mediaPlayer = MediaPlayer.create(this, R.raw.song);

        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.start();
                textStatus.setText("Status: Playing...");
            }
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                textStatus.setText("Status: Paused");
            }
        });

        btnStop.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                textStatus.setText("Status: Stopped");
                // Re-prepare media player agar bisa di-play kembali setelah stop
                try {
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.song);
                } catch (Exception e) {
                    Log.e(TAG, "Error re-preparing MediaPlayer", e);
                }
            }
        });
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
