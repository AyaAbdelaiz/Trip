package com.app.trip;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TripsAdapter extends RecyclerView.Adapter<TripsAdapter.ViewHolder> {

    private List<Trip> tripList;
    private Context context;
    private ItemClickListener mClickListener;

    public TripsAdapter(Context context, List<Trip> tripsList, ItemClickListener itemClickListener) {

        if (tripsList.size() != 0 && tripsList != null) {
            this.tripList = tripsList;
        }
        this.context = context;
        this.mClickListener = itemClickListener;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_holder_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Trip trip = tripList.get(position);
        holder.tripName.setText(trip.getTripName());
        holder.tripStartPoint.setText(trip.getStartPoint());
        holder.tripEndPoint.setText(trip.getEndPoint());
        holder.time.setText(trip.getTime());
        holder.date.setText(trip.getDate());
        holder.tripType.setText(trip.getTripType());
        holder.notes.setText(trip.getTripNotes());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mClickListener != null)
                    mClickListener.onItemClick(trip, position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return tripList.size();
    }


   /* @Override
    public int getItemCount() {
        return mData.size();
    }*/


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tripStartPoint, tripEndPoint, time, date, tripType, tripName,notes;

        ViewHolder(View itemView) {
            super(itemView);
            tripName = itemView.findViewById(R.id.trip_name);
            tripStartPoint = itemView.findViewById(R.id.trip_start);
            tripEndPoint = itemView.findViewById(R.id.trip_end);
            time = itemView.findViewById(R.id.trip_time);
            date = itemView.findViewById(R.id.trip_date);
            tripType = itemView.findViewById(R.id.trip_type);
            notes = itemView.findViewById(R.id.trip_note);
        }

    }

   /* Trip getItem(int id) {
        return mData.get(id);
    }
*/


    public interface ItemClickListener {
        void onItemClick(Trip view, int position);
    }
}