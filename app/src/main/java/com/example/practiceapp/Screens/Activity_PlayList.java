package com.example.practiceapp.Screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.practiceapp.DB.DBHelper;
import com.example.practiceapp.DataModel.Song;
import com.example.practiceapp.MainActivity;
import com.example.practiceapp.R;
import com.example.practiceapp.RecyclerAdapters.Adapter_SongItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Activity_PlayList extends AppCompatActivity {

    static ArrayList<Song> playListSongs;

    public static ArrayList<Song> getPlayListSongs() {
        return playListSongs;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list);

        ((FloatingActionButton) (findViewById(R.id.btn_ADD))).setOnClickListener(v-> {
            // do nothing for now
        });

        String playListName = getIntent().getStringExtra("playListName");

        DBHelper dbHelper = new DBHelper(this, playListName);
        playListSongs = dbHelper.getSongs();
        System.out.println(playListSongs.get(0).getSongName());
        RecyclerView recyclerView = findViewById(R.id.songsHolder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MainActivity.setCurrentState(MainActivity.States.IN_LIST);
        Adapter_SongItem adapter_songItem = new Adapter_SongItem(this);
        recyclerView.setAdapter(adapter_songItem);

    }
}