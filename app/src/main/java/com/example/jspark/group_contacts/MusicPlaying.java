package com.example.jspark.group_contacts;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by jspark on 2017-08-23.
 */

public class MusicPlaying extends MainActivity {
    MediaPlayer mediaPlayer;
    int playPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.musicplaying);

        Button btn_mp = (Button) findViewById(R.id.Playing_Music);
        Button btn_st = (Button) findViewById(R.id.Stoping_Music);

        btn_mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer = MediaPlayer.create(MusicPlaying.this, R.raw.redtaste);
                mediaPlayer.start();
            }
        });
        btn_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
        playPosition = mediaPlayer.getCurrentPosition();
        mediaPlayer.pause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.pause();
        playPosition = mediaPlayer.getCurrentPosition();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mediaPlayer.start();
        mediaPlayer.seekTo(playPosition);
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }
}
