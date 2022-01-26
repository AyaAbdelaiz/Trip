package com.app.trip;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class TripsAdapter extends FirebaseRecyclerAdapter<Trip, TripsAdapter.ViewHolder> {

    public TripsAdapter(
            @NonNull FirebaseRecyclerOptions<Trip> options) {
        super(options);
    }


    private ItemClickListener mClickListener;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trip_holder_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void  onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Trip model){
        //Trip trip = mData.get(position);
        holder.tripName.setText(model.getTripName());
        holder.tripStartPoint.setText(model.getStartPoint());
        holder.tripEndPoint.setText(model.getStartPoint());
        holder.time.setText(model.getTime());
        holder.date.setText(model.getDate());
        holder.tripType.setText(model.getTripType());
    }



   /* @Override
    public int getItemCount() {
        return mData.size();
    }*/


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tripStartPoint, tripEndPoint, time, date,tripType,tripName;

        ViewHolder(View itemView) {
            super(itemView);
            tripName=itemView.findViewById(R.id.tripName);
            tripStartPoint = itemView.findViewById(R.id.tripStartPoint);
            tripEndPoint = itemView.findViewById(R.id.tripEndPoint);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            tripType = itemView.findViewById(R.id.tripType);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

   /* Trip getItem(int id) {
        return mData.get(id);
    }
*/

    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}