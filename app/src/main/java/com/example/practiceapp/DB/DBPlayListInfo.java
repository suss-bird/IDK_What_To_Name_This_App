package com.example.practiceapp.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.practiceapp.DataModel.PlayList;

import java.util.ArrayList;

public class DBPlayListInfo extends SQLiteOpenHelper {

    private static final String DB_NAME = "playlistRecords.db";
    private static final int DB_VER = 1;
    private static final String TABLE_PLAYLISTS_INFO = "playlists";
    private static final String KEY_ID = "playlist_id";
    private static final String KEY_NAME = "list_name";

    public DBPlayListInfo(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS playlists" +
                "(playlist_id INTEGER PRIMARY KEY, list_name TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String DROP_Table = "DROP TABLE IF EXISTS playlists";
        db.execSQL(DROP_Table);
        onCreate(db);
    }

    public void insertPlaylist(PlayList playList) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, playList.getName());
        db.insert(TABLE_PLAYLISTS_INFO, null, values);
        db.close();
    }

    public ArrayList<PlayList> getPlayLists() {
        ArrayList<PlayList> playlists = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(
                TABLE_PLAYLISTS_INFO,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if(cursor.moveToFirst()) {
            do {
               playlists.add(new PlayList(cursor.getString(1)));
            } while (cursor.moveToNext());
        }
        return playlists;
    }
}
