package com.app.trip;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(entities = Trips.class,version = 1)
public abstract class TripRoomDatabase extends RoomDatabase {
    public static TripRoomDatabase instance;
    public abstract TripDao tripDao();

// singletone///
    public static synchronized TripRoomDatabase getInstance(Context context){
   if(instance==null){
       instance= Room.databaseBuilder(context.getApplicationContext(),TripRoomDatabase.class,"Trip_database")
               .fallbackToDestructiveMigration()
               .addCallback(roomcallBack)
               .build();

   }

return instance;
    }
    private static RoomDatabase.Callback roomcallBack= new RoomDatabase.Callback(){


        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new LoadDatabaseAsynkTaske(instance).execute();
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
    public static class LoadDatabaseAsynkTaske extends AsyncTask<Void,Void, List<Trips>>{
      private TripDao tripdao;
        LoadDatabaseAsynkTaske(TripRoomDatabase triproomdb){
             tripdao=triproomdb.tripDao();
        }

        @Override
        protected List<Trips> doInBackground(Void... voids) {

            tripdao.insert(new Trips("","","", 6.7, 3.5, 4.5, 3.2, "", "", "", "","","", "", "",1111));
            tripdao.insert(new Trips("","","", 6.7, 3.5, 4.5, 3.2, "", "", "", "","","", "", "",1111));
            tripdao.insert(new Trips("","","", 6.7, 3.5, 4.5, 3.2, "", "", "", "","","", "", "",1111));
            return null;
        }
    }
}
