package com.ubl.tugaspresentasi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LyricsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyrics);

        EditText editYoutubeLink = findViewById(R.id.editYoutubeLink);
        Button btnFetchLyrics = findViewById(R.id.btnFetchLyrics);
        TextView textSyncStatus = findViewById(R.id.textSyncStatus);

        btnFetchLyrics.setOnClickListener(v -> {
            String url = editYoutubeLink.getText().toString();
            if (url.contains("youtube.com") || url.contains("youtu.be")) {
                textSyncStatus.setText(R.string.status_sync_done);
                Toast.makeText(this, R.string.msg_simulated, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.error_invalid_link, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
