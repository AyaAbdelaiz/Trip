package com.app.trip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

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

        // call data from firebase
        FirebaseRecyclerOptions<Trip> options
                = new FirebaseRecyclerOptions.Builder<Trip>()
                .setQuery(FirebaseDatabase.getInstance().getReference("usersTrip"), Trip.class)
                .build();

        // inillization adapter
        tripsAdapter = new TripsAdapter(options);
        tripsAdapter.notifyDataSetChanged();
        tripsList.setAdapter(tripsAdapter);

    }

    // life cycle activity function
    @Override
    protected void onResume() {
        super.onResume();
        //refresh adapter data
        tripsAdapter.notifyDataSetChanged();
        tripsAdapter.startListening();
    }

    // life cycle activity function
    @Override
    protected void onStart() {
        super.onStart();
        tripsAdapter.startListening();
    }

    // Function to tell the app to stop getting
    // life cycle activity function
    @Override
    protected void onStop() {
        super.onStop();
        tripsAdapter.stopListening();
    }
}