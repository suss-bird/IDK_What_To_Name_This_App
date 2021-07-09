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

import com.example.practiceapp.DataModel.Song;
import com.example.practiceapp.MainActivity;
import com.example.practiceapp.R;

import java.io.IOException;
import java.util.ArrayList;


public class Activity_PlayScreen extends AppCompatActivity {
    boolean playPause;
    MediaPlayer mediaPlayer;
    int save;
    int currentID;
    String path;
//    ArrayList<Song> songs;
    public Activity_PlayScreen() {

    }

    public Activity_PlayScreen(ArrayList<Song> songs) {
//        this.songs = songs;
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_screen);

        ((TextView) (findViewById(R.id.txtVw_SongName))).setText(getIntent().getStringExtra("SongName"));
        Bundle args = getIntent().getBundleExtra("songsList");
//        songs = (ArrayList<Song>) args.getSerializable("songs");
        System.out.println(MainActivity.songs.size());
        path = getIntent().getStringExtra("SongPath");
        currentID = getIntent().getIntExtra("currentID", 0);
        mediaPlayer = new MediaPlayer();
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
            mediaPlayer.pause();
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 5000);
            mediaPlayer.start();
        });

        findViewById(R.id.btn_FBW).setOnClickListener(v -> {
            mediaPlayer.pause();
            mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() - 5000);
            mediaPlayer.start();
        });

        ((SeekBar)findViewById(R.id.seekbar_main)).setMax(mediaPlayer.getDuration());

        findViewById(R.id.btn_Next).setOnClickListener(v -> {
            nextSong();
        });


        Handler mHandler = new Handler();
        Activity_PlayScreen.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition();
                    ((SeekBar) findViewById(R.id.seekbar_main)).setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 10);
            }
        });


        ((SeekBar)findViewById(R.id.seekbar_main)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                    save = progress;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(save);
                mediaPlayer.start();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    public void nextSong() {
        currentID++;
        if(currentID == MainActivity.songs.size()) {
            currentID = 0;
            path = MainActivity.songs.get(currentID).getPath();
            System.out.println("Currently playing " + path);
            ((TextView) findViewById(R.id.txtVw_SongName)).setText(MainActivity.songs.get(currentID).getSongName());
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(path));
                    System.out.println("Currently playing " + path);
                    ((SeekBar)findViewById(R.id.seekbar_main)).setMax(mediaPlayer.getDuration());
                }
                mediaPlayer.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());

            }
        }
        else {
            path = MainActivity.songs.get(currentID).getPath();
            ((TextView) findViewById(R.id.txtVw_SongName)).setText(MainActivity.songs.get(currentID).getSongName());
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(path));
                    System.out.println("Currently playing " + path);
                    ((SeekBar)findViewById(R.id.seekbar_main)).setMax(mediaPlayer.getDuration());
                }
                mediaPlayer.start();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
