package com.example.android.popmovies2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "List of Favorite Movies";
    private static AppDatabase sAppDatabase;

    public static AppDatabase getInstance(Context context) {
        if (sAppDatabase==null){
            synchronized (LOCK) {
                sAppDatabase = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sAppDatabase;
    }

    public MovieDao movieDao;
}
