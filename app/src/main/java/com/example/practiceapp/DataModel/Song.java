package com.example.practiceapp.DataModel;

public class Song {
    int id;
    String songName;
    String path;

    public Song(String songName, String path) {
        this.songName = songName;
        this.path = path;
    }

    public String getSongName() {
        return songName;
    }

    public String getPath() {
        return path;
    }
}
