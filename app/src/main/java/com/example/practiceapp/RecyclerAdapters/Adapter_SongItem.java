package com.example.practiceapp.RecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceapp.DataModel.PlayList;
import com.example.practiceapp.DataModel.Song;
import com.example.practiceapp.MainActivity;
import com.example.practiceapp.R;
import com.example.practiceapp.Screens.Activity_PlayList;
import com.example.practiceapp.Screens.Activity_PlayScreen;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;

public class Adapter_SongItem extends RecyclerView.Adapter<Adapter_SongItem.ViewHolder> {

    Context context;
    public Adapter_SongItem(Context context) {
        this.context = context;
    }


    @NonNull
    @NotNull
    @Override
    public Adapter_SongItem.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        if(MainActivity.getCurrentState() == MainActivity.States.TRACKS) {
            ((TextView) holder.itemView.findViewById(R.id.songName)).setText(MainActivity.songs.get(position).getSongName());

            holder.itemView.findViewById(R.id.SongItem).setOnClickListener(v -> {
                Intent intent = new Intent(context, Activity_PlayScreen.class);
                intent.putExtra("SongName", MainActivity.songs.get(position).getSongName());
                Bundle bundle = new Bundle();
//            bundle.putSerializable("songs", (Serializable)songs);
//            intent.putExtra("songsList", bundle);
                intent.putExtra("SongPath", MainActivity.songs.get(position).getPath());
                intent.putExtra("currentID", position);
                context.startActivity(intent);
            });
        }
        else if(MainActivity.getCurrentState() == MainActivity.States.PLAYLIST) {
            ((TextView) holder.itemView.findViewById(R.id.songName)).setText(MainActivity.playlists.get(position).getName());
            ((TextView) holder.itemView.findViewById(R.id.songTotalTime)).setText(Integer.toString(MainActivity.playlists.size()));
            holder.itemView.findViewById(R.id.SongItem).setOnClickListener(v -> {
                // start playlists intent
                Intent intent = new Intent(context, Activity_PlayList.class);
                intent.putExtra("playListName", MainActivity.playlists.get(position).getName());
                context.startActivity(intent);
            });
        }
        else if(MainActivity.getCurrentState() == MainActivity.States.IN_LIST) {
            ((TextView) holder.itemView.findViewById(R.id.songName)).setText(Activity_PlayList.getPlayListSongs().get(position).getSongName());

            holder.itemView.findViewById(R.id.SongItem).setOnClickListener(v -> {
                Intent intent = new Intent(context, Activity_PlayScreen.class);
                intent.putExtra("SongName", Activity_PlayList.getPlayListSongs().get(position).getSongName());
                intent.putExtra("SongPath", Activity_PlayList.getPlayListSongs().get(position).getPath());
                intent.putExtra("currentID", position);
                context.startActivity(intent);
            });
        }
    }


    @Override
    public int getItemCount() {
        // create two more adapters
        // and pass the arraylist to each one as a parameter
        if(MainActivity.getCurrentState() == MainActivity.States.TRACKS)
            return MainActivity.songs.size();
        else if(MainActivity.getCurrentState() == MainActivity.States.PLAYLIST)
            return MainActivity.playlists.size();
        else {
            System.out.println("Here");
            return Activity_PlayList.getPlayListSongs().size();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
