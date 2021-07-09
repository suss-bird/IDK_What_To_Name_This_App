package com.example.practiceapp.RecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceapp.DB.DBHelper;
import com.example.practiceapp.DB.DBPlayListInfo;
import com.example.practiceapp.DataModel.PlayList;
import com.example.practiceapp.DataModel.Song;
import com.example.practiceapp.MainActivity;
import com.example.practiceapp.R;
import com.example.practiceapp.Screens.Activity_PlayScreen;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AdapterSongAdder extends RecyclerView.Adapter<AdapterSongAdder.ViewHolder> {

    Context context;
    ArrayList<Song> songs;
    public AdapterSongAdder(Context context) {
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterSongAdder.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ((TextView)holder.itemView.findViewById(R.id.songName)).setText(MainActivity.songs.get(position).getSongName());

        holder.itemView.findViewById(R.id.SongItem).setOnClickListener(v -> {
            v.setBackgroundColor(Color.GREEN);
            MainActivity.songsToAdd.add(MainActivity.songs.get(position));
            // now add the shit to DB
            // Better not to add songs the moment you create playlist...
        });
    }


    @Override
    public int getItemCount() {
        // create two more adapters
        // and pass the arraylist to each one as a parameter

        return MainActivity.songs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}