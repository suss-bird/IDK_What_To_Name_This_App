package com.example.practiceapp.Screens;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.practiceapp.MainActivity;
import com.example.practiceapp.R;

import java.io.IOException;

public class Activity_PlayScreen extends AppCompatActivity {
    boolean playPause;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        ((TextView) (findViewById(R.id.txtVw_SongName))).setText(getIntent().getStringExtra("SongName"));

        String path = getIntent().getStringExtra("SongPath");

        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
        playPause = true;

        findViewById(R.id.btn_PlayPause).setOnClickListener(v -> {
            if(playPause) {
                mediaPlayer.pause();
                ((ImageView) v).setImageResource(R.drawable.ic_playbtn);
                playPause = false;
            }
            else {
                mediaPlayer.start();
                ((ImageView) v).setImageResource(R.drawable.ic_pausebtn);
                playPause = true;
                // works like a charm xd
            }
        });

        findViewById(R.id.btn_FF).setOnClickListener(v -> {
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 10);
        });
        ((SeekBar)findViewById(R.id.seekbar_main)).setMax(mediaPlayer.getDuration());

        Handler mHandler = new Handler();
        Activity_PlayScreen.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition();
                    ((SeekBar) findViewById(R.id.seekbar_main)).setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        ((SeekBar)findViewById(R.id.seekbar_main)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}
