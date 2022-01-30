package com.app.trip;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class ReposatoryTrip {
    private TripDao mtripdao;
    private LiveData<List<Trips>> getAllTrips;





    public ReposatoryTrip(Application application){
        TripRoomDatabase tripRoomDatabase=TripRoomDatabase.getInstance(application);
        mtripdao=tripRoomDatabase.tripDao();
       getAllTrips=mtripdao.getAll();


    }

public LiveData<List<Trips>>getGetAllTrips(){return getAllTrips;}
    public void inserte(Trips trips){new InserteAsyncTask(mtripdao).execute();}
    public void delete(Trips trips){new DeleteAsyncTask(mtripdao).execute();}
    public void update(Trips trips){new UpdateAsyncTask(mtripdao).execute();}
    public void deleteAllTrips(){new DeleteAllAsyncTask(mtripdao).execute();}


    private static class InserteAsyncTask extends AsyncTask<Trips,Void,Void>{

          public TripDao tripDao;
          public InserteAsyncTask(TripDao tripDao){
           this.tripDao=tripDao;

          }
        @Override
        protected Void doInBackground(Trips... trips) {
              tripDao.insert(trips[0]);
            return null;
        }
    }






    private static class DeleteAsyncTask extends AsyncTask<Trips,Void,Void>{

        public TripDao tripDao;
        public DeleteAsyncTask(TripDao tripDao){
            this.tripDao=tripDao;

        }
        @Override
        protected Void doInBackground(Trips... trips) {
            tripDao.delet(trips[0]);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Trips,Void,Void>{

        public TripDao tripDao;
        public UpdateAsyncTask(TripDao tripDao){
            this.tripDao=tripDao;

        }
        @Override
        protected Void doInBackground(Trips... trips) {
            tripDao.update(trips[0]);
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{

        public TripDao tripDao;
        public DeleteAllAsyncTask(TripDao tripDao){
            this.tripDao=tripDao;

        }

        @Override
        protected Void doInBackground(Void... voids) {
            tripDao.deleteAllTrips();
            return null;
        }
    }
}
