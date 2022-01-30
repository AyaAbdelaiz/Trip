package com.app.trip;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ViewModel extends AndroidViewModel {

    private ReposatoryTrip reposatoryTrip;
    private LiveData<List<Trips>> alltrips;

    public ViewModel(@NonNull Application application) {
        super(application);
        reposatoryTrip=new ReposatoryTrip(application);
        alltrips=reposatoryTrip.getGetAllTrips();
    }
    public void insert(Trips trips){
        reposatoryTrip.inserte(trips);
    }
    public void delet(Trips trips){
        reposatoryTrip.delete(trips);
    }
    public void update(Trips trips){
        reposatoryTrip.update(trips);
    }
    public void deletAllTrips(){
        reposatoryTrip.deleteAllTrips();
    }
    public LiveData<List<Trips>> getAllTRips(){
        return alltrips;
    }
}
