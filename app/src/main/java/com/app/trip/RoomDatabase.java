package com.app.trip;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

//@Database(entities = {Trip.class}, version = 1)
public class RoomDatabase {

    /*private Context mCtx;
    private static RoomDatabase mInstance;

    //our app database object
    private AppDatabase appDatabase;

    private RoomDatabase(Context mCtx) {
        this.mCtx = mCtx;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(mCtx, AppDatabase.class, "MyToDos").build();
    }

    public static synchronized RoomDatabase getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new RoomDatabase(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }*/
}