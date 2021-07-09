package com.example.practiceapp.RecyclerAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practiceapp.DataModel.PlayList;
import com.example.practiceapp.MainActivity;
import com.example.practiceapp.R;
import com.example.practiceapp.Screens.Activity_PlayList;
import com.example.practiceapp.Screens.Activity_PlayScreen;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter_PlayLists extends RecyclerView.Adapter<Adapter_PlayLists.ViewHolder> {

    Context context;
    ArrayList<PlayList> playLists;
    public Adapter_PlayLists(Context context, ArrayList<PlayList> playLists) {
        this.context = context;
        this.playLists = playLists;
    }

    public void setPlayLists(ArrayList<PlayList> playLists) {
        this.playLists = playLists;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter_PlayLists.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_view_song, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ((TextView)holder.itemView.findViewById(R.id.songName)).setText(playLists.get(position).getName());

        holder.itemView.findViewById(R.id.SongItem).setOnClickListener(v -> {
            // bat shit
        });
    }


    @Override
    public int getItemCount() {
        // create two more adapters
        // and pass the arraylist to each one as a parameter

        return playLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}
