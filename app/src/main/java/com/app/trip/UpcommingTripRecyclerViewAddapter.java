package com.app.trip;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.trip.database.Trip;

import java.util.List;

public class UpcommingTripRecyclerViewAddapter extends RecyclerView.Adapter<UpcommingTripRecyclerViewAddapter.UpcomingViewHolder> {

    List <Trip>tripList;
    Context context;
    Activity activity;


    public UpcommingTripRecyclerViewAddapter(Context context, List TrripInfoList, Activity activity){

        this.context = context;
        this.tripList=TrripInfoList;
        this.activity = activity;

    }


    @NonNull
    @Override
    public UpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.upcoming_row, parent, false);

        return new UpcomingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcommingTripRecyclerViewAddapter.UpcomingViewHolder holder, int position) {

        Trip trip= (Trip) tripList.get(position);
        holder.nameTxt.setText(trip.getTripName());
        holder.timeTxt.setText(trip.getTime());
        holder.dateTxt.setText(trip.getDate());
        holder.fromTxt.setText(trip.getStartPoint());
        holder.toTxt.setText(trip.getEndPoint());


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class UpcomingViewHolder extends RecyclerView.ViewHolder {

        TextView fromTxt,toTxt,nameTxt,timeTxt,dateTxt;
        Button start_btn;
        Button Note;
        public UpcomingViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt=itemView.findViewById(R.id.nameLable);
            timeTxt=itemView.findViewById(R.id.time);
            dateTxt=itemView.findViewById(R.id.date);
            fromTxt=itemView.findViewById(R.id.from);
            toTxt=itemView.findViewById(R.id.to);
            start_btn=itemView.findViewById(R.id.startBtn);
            Note=itemView.findViewById(R.id.notesBtn);


        }
    }
}
