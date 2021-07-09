package com.example.practiceapp.DataModel;

import java.io.Serializable;

public class Song {
    int id;
    String songName;
    String path;

    public Song(int id, String songName, String path) {
        this.id = id;
        this.songName = songName;
        this.path = path;
    }

    public String getSongName() {
        return songName;
    }

    public int getID() {
        return id;
    }

    public String getPath() {
        return path;
    }
}
