package com.example.practiceapp;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceapp.DB.DBHelper;
import com.example.practiceapp.DB.DBPlayListInfo;
import com.example.practiceapp.DataModel.PlayList;
import com.example.practiceapp.DataModel.Song;
import com.example.practiceapp.RecyclerAdapters.AdapterSongAdder;
import com.example.practiceapp.RecyclerAdapters.Adapter_PlayLists;
import com.example.practiceapp.RecyclerAdapters.Adapter_SongItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Song> songs;
    public static ArrayList<PlayList> playlists;
    public static ArrayList<Song> songsToAdd;
    RecyclerView recyclerView;
    Adapter_SongItem adapterSongItem;
    public enum States {TRACKS, PLAYLIST, FAVS, IN_LIST}
    static States currentState;

    public static States getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(States currentState) {
        MainActivity.currentState = currentState;
    }

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        songs = new ArrayList<>();
        playlists = new ArrayList<>();
        songsToAdd = new ArrayList<>();
        loadSongs();
        currentState = States.TRACKS;
        recyclerView = findViewById(R.id.songsHolder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        findViewById(R.id.btn_ADD).setVisibility(View.INVISIBLE);
        adapterSongItem = new Adapter_SongItem(this);
        recyclerView.setAdapter(adapterSongItem);

        findViewById(R.id.btn_ShowTracks).setOnClickListener(v -> {
            loadSongs();
            currentState = States.TRACKS;
            recyclerView.setAdapter(adapterSongItem);
            adapterSongItem.notifyDataSetChanged();
            findViewById(R.id.btn_ADD).setVisibility(View.INVISIBLE);
        });

        findViewById(R.id.btn_ShowPlaylists).setOnClickListener(v -> {
            loadPlaylist();
            currentState = States.PLAYLIST;
            recyclerView.setAdapter(adapterSongItem);
            adapterSongItem.notifyDataSetChanged();
            findViewById(R.id.btn_ADD).setVisibility(View.VISIBLE);
        });

        findViewById(R.id.btn_ShowFavs).setOnClickListener(v -> {
            loadPlaylist();
            currentState = States.FAVS;
            adapterSongItem.notifyDataSetChanged();
            findViewById(R.id.btn_ADD).setVisibility(View.VISIBLE);
        });

        findViewById(R.id.btn_ADD).setOnClickListener(v-> {
            if(currentState == States.TRACKS) { }
            else if (currentState == States.PLAYLIST) {
                // show playlists adder
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.popup_create_playlist, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                RecyclerView recyclerView = popupView.findViewById(R.id.songsHolderToAdd);

                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                AdapterSongAdder adapterSongAdder = new AdapterSongAdder(this);
                recyclerView.setAdapter(adapterSongAdder);

                popupView.findViewById(R.id.btn_CreatePlaylist).setOnClickListener( v1 -> {
                    String PlayListName = ((TextInputEditText) (popupView.findViewById(R.id.txtEdt_PlayListName))).getText().toString();
                    DBPlayListInfo dbPlayListInfo = new DBPlayListInfo(this);
                    dbPlayListInfo.insertPlaylist(new PlayList(PlayListName));
                    adapterSongItem.notifyDataSetChanged();
                    DBHelper dbHelper = new DBHelper(this, PlayListName);
                    for (Song song : songsToAdd) {
                        dbHelper.insertSong(song);
                    }
                    popupWindow.dismiss();
                    adapterSongAdder.notifyDataSetChanged();
                });
            }
            else {
                // show fav adder
            }
        });
        TextInputEditText searcher = findViewById(R.id.txtEdt_Search);
    }

    void loadSongs() {
        songs = new ArrayList<>();

        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
        int count = 0;

        if(musicCursor != null) {
            while (musicCursor.moveToNext()) {
                String path = musicCursor.getString(musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA));
                String name = musicCursor.getString(musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE));
                System.out.println(name);
                songs.add(new Song(count, name, path));
            }
        }
        assert musicCursor != null;
        musicCursor.close();
    }

    void loadPlaylist() {
        playlists = new ArrayList<>();
        DBPlayListInfo dbPlayListInfo = new DBPlayListInfo(this);

        playlists = dbPlayListInfo.getPlayLists();
        // send to recycler ....
        // based on currentState that u created dumbass
    }
}
