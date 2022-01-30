package com.app.trip;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class upcoming extends Fragment{ //implements UpcommingTripRecyclerViewAddapter.OnItemClickLisener {
    /*private static final String TAG = "UpcomingFragment";
    RecyclerView recyclerView;
    UpcommingTripRecyclerViewAddapter upcommingTripRecyclerViewAddapter;
    List<Trips> upcomingList = new ArrayList<>();
    TripRoomDatabase database;
    private ViewModel viewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = TripRoomDatabase.getInstance(getContext());
        database = TripRoomDatabase.getInstance(getContext());
        viewModel= ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllTRips().observe(this, new Observer<List<Trips>>() {
            @Override
            public void onChanged(List<Trips> trips) {
                Toast.makeText(requireActivity()
                        ,"its database",Toast.LENGTH_LONG).show();
            }
        });

    }

 *//*   @Override
    public void onResume() {
        super.onResume();

        ///////firebase connected with room database

        database.tripDao().getUpcomingTripsByUser(FirebaseAuth.getInstance().getUid()).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<List<Trips>>() {
            @Override
            public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                Log.i(TAG, "onSubscribe: " + FirebaseAuth.getInstance().getUid());
            }

            @Override
            public void onSuccess(@io.reactivex.annotations.NonNull List<Trips> trips) {
                if (trips.size() == 0)
                    Toast.makeText(getContext(), "You have no upcoming trips", Toast.LENGTH_SHORT).show();

                upcomingList = trips;
                upcommingTripRecyclerViewAddapter.setList(trips);
                Log.i(TAG, "onSuccess: ");

                // upcommingTripRecyclerViewAddapter.notifyDataSetChanged();

            }


            @Override
            public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                Log.i(TAG, "onError: ");

            }
        });
    }*//*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_upcoming, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity upcomingFragment = getActivity();
        recyclerView = view.findViewById(R.id.upcomingRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager LayoutManager = new LinearLayoutManager(upcomingFragment);
        LayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(LayoutManager);
        upcommingTripRecyclerViewAddapter = new UpcommingTripRecyclerViewAddapter(upcomingFragment, upcomingList);
        upcommingTripRecyclerViewAddapter.setOnItemClickLisener(this);
        recyclerView.setAdapter(upcommingTripRecyclerViewAddapter);
    }


    /////////////////////////////implemented methods:

    @Override
    public void onSuccess(@NonNull List<Trips> trips) {

    }

    @Override
    public void onCancelClickLisener(Trips trip) {
        //openDialog(getContext(),trip);


    }

    @Override
    public void onStartClickLisener(int positon, String to, Trips trip) {
        //  alarmHelper.cancelAlarm(trip);
        if (checkFloatingViewServicePermission()) {
            setDoneStatus(upcomingList.get(positon).getId());
            Uri gmmIntentUri = Uri.parse("http://maps.google.com/maps?daddr=" + to);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
            //  getContext().startService(new Intent(getContext(), FloatingViewService.class).putExtra("notes",new Gson().toJson(upcomingList.get(position).getNotes())));
            // startFloatingViewService(new Gson().toJson(upcomingList.get(position).getNotes()));
        } else {
            askPermission();
            Toast.makeText(getContext(), "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onViewNotesClickListener(int posation) {
        if (upcomingList.get(posation).getNotes() != null && !upcomingList.get(posation).getNotes().isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putString("notes", new Gson().toJson(upcomingList.get(posation).getNotes()));
            AddNotes addNotes = new AddNotes();

*//*            addNotes.setArguments(bundle);
            addNotes.show(getParentFragmentManager(),"note_dialog");*//*

        } else {
            Toast.makeText(getContext(), "This item doesn't have any notes", Toast.LENGTH_LONG).show();
        }


    }


    private void askPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + getContext().getPackageName()));
        // startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
    }

    private boolean checkFloatingViewServicePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if (Settings.canDrawOverlays(getContext()))
            return true;

        return false;
    }

  *//*  private void startFloatingViewService(String notes) {
        getContext().startService(new Intent(getContext(), FloatingViewService.class).putExtra("notes",notes));
    }*//*

    public void setDoneStatus(int tripID) {
        database = TripRoomDatabase.getInstance(getContext());
    }
*//*
        database.tripDao().tripStarted(tripID).subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {


                    }

                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                    }
                });



*//*

*/
}
