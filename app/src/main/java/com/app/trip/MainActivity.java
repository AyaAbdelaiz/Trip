package com.app.trip;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab_add;
    NavigationView navigationView;
    TextView textView;
    FirebaseInstance instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        instance=new FirebaseInstance();
        fab_add = findViewById(R.id.fab_add);
        navigationView = findViewById(R.id.navigationView);
        View view = navigationView.getHeaderView(0);


        textView = view.findViewById(R.id.email);
        textView.setText(instance.getUserEmail());

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddTrip.class));
            }
        });


    }
}