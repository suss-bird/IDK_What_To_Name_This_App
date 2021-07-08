package com.example.practiceapp.RecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceapp.DataModel.Song;
import com.example.practiceapp.MainActivity;
import com.example.practiceapp.R;
import com.example.practiceapp.Screens.Activity_PlayScreen;

import org.jetbrains.annotations.NotNull;

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
        ((TextView)holder.itemView.findViewById(R.id.songName)).setText(MainActivity.songs.get(position).getSongName());

        holder.itemView.findViewById(R.id.SongItem).setOnClickListener(v -> {
            Intent intent = new Intent(context, Activity_PlayScreen.class);
            intent.putExtra("SongName", MainActivity.songs.get(position).getSongName());

            intent.putExtra("SongPath", MainActivity.songs.get(position).getPath());
            context.startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return MainActivity.songs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
