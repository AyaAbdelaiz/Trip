package com.app.trip;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.trip.database.Trip;

import java.util.ArrayList;
import java.util.List;

public class upcoming extends Fragment {

    RecyclerView tripRecyclerView;
    private UpcommingTripRecyclerViewAddapter upcommingTripRecyclerViewAddapter;

    private List tripsList = new ArrayList<Trip>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tripRecyclerView = view.findViewById(R.id.upcomingRecycler);
        upcommingTripRecyclerViewAddapter=new UpcommingTripRecyclerViewAddapter(getContext(),tripsList,getActivity());

    }

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         return inflater.inflate(R.layout.fragment_upcoming,container,false)  ;

    }
}
