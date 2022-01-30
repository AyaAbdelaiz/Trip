package com.app.trip;

import static com.app.trip.AddNotes.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class UpcommingTripRecyclerViewAddapter extends RecyclerView.Adapter<UpcommingTripRecyclerViewAddapter.UpcomingViewHolder> {

    List <Trips>tripList;
    Context context;
    Activity activity;
    OnItemClickLisener onItemClickLisener;
    public void setOnItemClickLisener(OnItemClickLisener onItemClickLisener) {
        this.onItemClickLisener = onItemClickLisener;

    }

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

        Trips trip= (Trips) tripList.get(position);
        holder.nameTxt.setText(trip.getTripName());
        holder.timeTxt.setText(trip.getTime());
        holder.dateTxt.setText(trip.getDate());
        holder.fromTxt.setText(trip.getStartPoint());
        holder.toTxt.setText(trip.getEndPoint());

        holder.start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onItemClickLisener.onStartClickLisener(position,holder.toTxt.getText().toString().
                              replace(" ","+"),tripList.get(position));

            }
        });
holder.viewNoteBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onItemClickLisener.onViewNotesClickListener(position);

    }
});

holder.editTripBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Trips trip = tripList.get(position);
        Intent intent = new Intent(holder.itemView.getContext(), Add_Trip.class);
        //////////////////////
         intent.putExtra("Trip", (Parcelable) trip);
        holder.itemView.getContext().startActivity(intent);
    }
});

holder.cancelTripBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onItemClickLisener.onCancelClickLisener(tripList.get(position));
    }
});
holder.Note.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent  intent=new Intent(holder.itemView.getContext(),AddNotes.class);
        intent.putExtra("trip",new Gson().toJson(tripList.get(position)));
        holder.itemView.getContext().startActivity(intent);
    }
});


    }


    public interface OnItemClickLisener{
        void onSuccess(@io.reactivex.annotations.NonNull List<Trips> trips);

        void onCancelClickLisener(Trips trip);
        void onStartClickLisener(int positon, String to, Trips trip);
        void onViewNotesClickListener(int posation);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class UpcomingViewHolder extends RecyclerView.ViewHolder {

        TextView fromTxt,toTxt,nameTxt,timeTxt,dateTxt;
        Button start_btn;
        Button Note;
        public ImageButton cancelTripBtn, editTripBtn, viewNoteBtn;

        public UpcomingViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt=itemView.findViewById(R.id.name);
            timeTxt=itemView.findViewById(R.id.time);
            dateTxt=itemView.findViewById(R.id.date);
            fromTxt=itemView.findViewById(R.id.from);
            toTxt=itemView.findViewById(R.id.to);
            start_btn=itemView.findViewById(R.id.startBtn);
            Note=itemView.findViewById(R.id.addnote);
            cancelTripBtn=itemView.findViewById(R.id.canceldbtn);
            editTripBtn=itemView.findViewById(R.id.editbtn);
            viewNoteBtn=itemView.findViewById(R.id.savednote);

        }
    }
    public UpcommingTripRecyclerViewAddapter(Context _context, List<Trips> tripList)
    {
        this.context = _context;
        this.tripList = tripList;
        Log.i(TAG, "UpcomingAdapter: ");
    }

    public void setList(List<Trips> tripList) {
        this.tripList = tripList;
    }


}

