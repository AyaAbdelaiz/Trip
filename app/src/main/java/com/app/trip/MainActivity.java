package com.app.trip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements TripsAdapter.ItemClickListener {

    FloatingActionButton fab_add;
    NavigationView navigationView;
    TextView textView;
    FirebaseInstance instance;
    RecyclerView tripsList;
    TripsAdapter tripsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        instance = new FirebaseInstance();
        fab_add = findViewById(R.id.fab_add);
        navigationView = findViewById(R.id.navigationView);
        View view = navigationView.getHeaderView(0);
        tripsList = findViewById(R.id.recyclerView);
        tripsList.setLayoutManager(new LinearLayoutManager(this));


        textView = view.findViewById(R.id.email);
        textView.setText(instance.getUserEmail());

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTrip.class));
            }
        });

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference database = instance.getDatabaseReference();
        String id = instance.getUserId();
        DatabaseReference reference = database1.getReference(id).child("trips");

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GenericTypeIndicator<List<Trip>> genericTypeIndicator = new GenericTypeIndicator<List<Trip>>() {
                };

                List<Trip> trips = snapshot.getValue(genericTypeIndicator);
                // Log.d("Ayaa", "onDataChange: " + trips.get(0).getTripName());
                try {
                    tripsAdapter = new TripsAdapter(MainActivity.this, trips, MainActivity.this);
                    tripsAdapter.notifyDataSetChanged();
                    tripsList.setAdapter(tripsAdapter);
                } catch (Exception o) {
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       /* // call data from firebase
        FirebaseRecyclerOptions<Trip> options
                = new FirebaseRecyclerOptions.Builder<Trip>()
                .setQuery(FirebaseDatabase.getInstance().getReference("0"), Trip.class)
                .build();
*/
        // inillization adapter

    }

    // life cycle activity function
    @Override
    protected void onResume() {
        super.onResume();
        //refresh adapter data
        if (tripsAdapter != null)
            tripsAdapter.notifyDataSetChanged();
    }


    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onItemClick(Trip view, int position) {
        Intent intent = new Intent(MainActivity.this, AddTrip.class);
        intent.putExtra("tripName", view.getTripName());
        intent.putExtra("tripData", view.getDate());
        intent.putExtra("tripTime", view.getTime());
        intent.putExtra("tripStart", view.getStartPoint());
        intent.putExtra("tripEnd", view.getEndPoint());
        intent.putExtra("tripType", view.getTripType());
        intent.putExtra("tripNotes", view.getTripNotes());
        intent.putExtra("position",position);
        startActivity(intent);
    }
}