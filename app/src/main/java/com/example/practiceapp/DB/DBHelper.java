package com.example.practiceapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.practiceapp.DataModel.Song;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "songsRecords.db";
    private static final int DB_VER = 1;
    private final String TABLE_SONG_INFO;
    private static final String KEY_ID = "song_id";
    private static final String KEY_NAME = "song_name";
    private static final String KEY_PATH = "song_path";

    public DBHelper(Context context, String TABLE_SONG_INFO) {
        super(context, DB_NAME, null, DB_VER);
        this.TABLE_SONG_INFO = TABLE_SONG_INFO;
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // creating database tables at runtime lol
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SONG_INFO +
                " (song_id INTEGER PRIMARY KEY, song_name TEXT, song_path TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_Table = "DROP TABLE IF EXISTS " + TABLE_SONG_INFO;
        db.execSQL(DROP_Table);
        onCreate(db);
    }

    public void insertSong(Song songs) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, songs.getID());
        values.put(KEY_NAME, songs.getSongName());
        values.put(KEY_PATH, songs.getPath());
        db.insert(TABLE_SONG_INFO, null, values);
        db.close();
    }

    public ArrayList<Song> getSongs() {
        ArrayList<Song> songs = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                TABLE_SONG_INFO,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if(cursor.moveToFirst()) {
            do {
                songs.add(new Song(cursor.getInt(0) ,cursor.getString(1), cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return songs;
    }
}