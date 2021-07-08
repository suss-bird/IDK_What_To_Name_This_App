package com.example.practiceapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceapp.DataModel.Song;
import com.example.practiceapp.RecyclerAdapters.Adapter_SongItem;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Song> songs;
    RecyclerView recyclerView;
    Adapter_SongItem adapterSongItem;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songs = new ArrayList<>();
        loadSongs();

        recyclerView = findViewById(R.id.songsHolder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapterSongItem = new Adapter_SongItem(this);
        recyclerView.setAdapter(adapterSongItem);


    }

    void loadSongs() {
        songs = new ArrayList<>();

        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);


        if(musicCursor != null) {
            while (musicCursor.moveToNext()) {
                String path = musicCursor.getString(musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA));
                String name = musicCursor.getString(musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE));
                System.out.println(name);
                songs.add(new Song(name, path));
            }
        }
        assert musicCursor != null;
        musicCursor.close();
    }
}
