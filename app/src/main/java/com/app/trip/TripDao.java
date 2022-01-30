package com.app.trip;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface TripDao {
    @Insert
    Completable insert(Trips trips);
    @Update
    Completable update(Trips trips);
    @Delete
    Completable delet(Trips trips);

    @Query("SELECT * FROM triptable ")
    LiveData< List<Trips> >getAll();

    @Query("SELECT * FROM triptable WHERE id = :id")
    Trips getTrip(int id);

    @Query("SELECT * FROM triptable")
    LiveData<List<Trips>> deleteAllTrips();

/*

   @Query("SELECT * FROM triptable WHERE userID = :id AND tripStatus = 'Upcoming'")
    LiveData<List<Trips>> getUpcomingTripsByUser(String id);


    @Query("SELECT * FROM triptable WHERE userID = :id AND (tripStatus = 'Done' or tripStatus = 'Cancelled')")
    LiveData<List<Trips>> getPastTripsByUser(String id);
*/


/*
    @Query("UPDATE triptable SET notes=:notes WHERE id = :id")
    Completable update(String notes, int id);
*/

/*
    @Query("UPDATE triptable SET tripStatus='Done' WHERE id = :id")
    Completable tripStarted(int id);
*/

/*    @Query("UPDATE triptable SET tripStatus='Cancelled' WHERE id = :id")
    Completable tripCancelled(int id);*/

   @Query("SELECT * FROM triptable WHERE userID = :id")
    LiveData<List<Trips>> getTripsByUser(String id);

/*
    @Query("DELETE FROM triptable")
    Completable deleteAllRecords();
*/






}
