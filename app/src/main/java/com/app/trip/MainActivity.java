package com.app.trip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton myFab ;
    private ViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel= ViewModelProviders.of(this).get(ViewModel.class);
        viewModel.getAllTRips().observe(this, new Observer<List<Trips>>() {
            @Override
            public void onChanged(List<Trips> trips) {
                Toast.makeText(MainActivity.this
                        ,"its database", Toast.LENGTH_LONG).show();
            }
        });
        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ImageView image = findViewById(R.id.imageMenu);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.history:
                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        fragmentManager2.beginTransaction().replace(R.id.nav_host_fragment, new history()).commit();
                        drawerLayout.closeDrawers();


                        break;
                    case R.id.Sync:
                        FragmentManager fragmentManager3 = getSupportFragmentManager();
                        fragmentManager3.beginTransaction().replace(R.id.nav_host_fragment, new sync()).commit();
                        drawerLayout.closeDrawers();


                        break;
                    case R.id.Up_coming:
                        FragmentManager fragmentManager4 = getSupportFragmentManager();
                        fragmentManager4.beginTransaction().replace(R.id.nav_host_fragment, new upcoming()).commit();
                        drawerLayout.closeDrawers();

                        break;

                }
                return false;
            }
        });
        myFab= findViewById(R.id.fab_add);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this ,Add_Trip.class );
                startActivity(intent);
            }
        });
    }
}