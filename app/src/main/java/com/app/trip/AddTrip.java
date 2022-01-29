package com.app.trip;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTrip extends AppCompatActivity {

    TextView date_picker_actions, time_picker_actions, edtStartPoint1, edEndPoint;
    Toolbar toolbar;
    TextInputLayout edtTripNote, edtTripName;
    LinearLayout searchLayout;
    Button addTrip;
    String note;
    String name_of_trip;
    double startPLongtuide, startPattuide, endPLongtudie, endPattuide;
    FirebaseInstance instance;
    RadioGroup radioGroup;
    RadioButton rd_rounded, rd_go_rounded;
    String tripType = "";
    int TripMonth = 1, TripDay = 0, TripYear = 0, pos;

    String placesApiToken = "AIzaSyAzyToZ95jgePDTBDOZc5wNqXeihsJ31eg";

    PlacesClient placesClient;
    String trbName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        toolbar = findViewById(R.id.trip_toolbar);
        edtStartPoint1 = findViewById(R.id.edtStartPoint1);
        edEndPoint = findViewById(R.id.edtEndPoint1);
        setSupportActionBar(toolbar);
        edtTripNote = findViewById(R.id.edtTripNote);
        edtTripName = findViewById(R.id.edtTripName);
        date_picker_actions = findViewById(R.id.date_picker_actions);
        time_picker_actions = findViewById(R.id.time_picker_actions);
        rd_rounded = findViewById(R.id.rd_rounded);
        searchLayout = findViewById(R.id.search_layut);
        rd_go_rounded = findViewById(R.id.rd_go_rounded);

        radioGroup = (RadioGroup) findViewById(R.id.rdGroup);

        try {
            trbName = getIntent().getStringExtra("tripName");
            String tripData = getIntent().getStringExtra("tripData");
            String tripTime = getIntent().getStringExtra("tripTime");
            String tripStart = getIntent().getStringExtra("tripStart");
            String tripEnd = getIntent().getStringExtra("tripEnd");
            String tripType = getIntent().getStringExtra("tripType");
            String tripNotes = getIntent().getStringExtra("tripNotes");
            pos = getIntent().getIntExtra("position", 0);


            edtTripName.getEditText().setText(trbName);
            edtStartPoint1.setText(tripStart);
            edEndPoint.setText(tripEnd);
           // edtTripNote.getEditText().setText(tripNotes);
            date_picker_actions.setText(tripData);
            time_picker_actions.setText(tripTime);
            edtTripNote.getEditText().setText(tripNotes);


            if (tripType.equals(rd_rounded.getText().toString())) {
                rd_rounded.setChecked(true);
                rd_go_rounded.setChecked(false);
            } else if (tripType.equals(rd_go_rounded.getText().toString())) {
                rd_go_rounded.setChecked(true);
                rd_rounded.setChecked(false);
            }

        } catch (Exception ex) {
            Log.d("rd_rounded", "onCreate: " + ex.getMessage());
        }


        instance = new FirebaseInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


        edtStartPoint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGoogleSearch("isStart");
            }
        });


        edEndPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGoogleSearch("isEnd");
            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton radioButton = (RadioButton) findViewById(i);
                tripType = radioButton.getText().toString();
            }
        });

        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        placesClient = Places.createClient(this);


        // getActionBar().setTitle("Add trip");


        addTrip = findViewById(R.id.btnAdd);
        date_picker_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateCalendar();
                // startActivity(new Intent(AddTrip.this, GoogleSearchActivity.class));

            }
        });

        time_picker_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePiker();
            }
        });


        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //name_of_trip = edtTripName.getEditText().getText().toString();
                // note = edtTripNote.getEditText().getText().toString();
                uploadTripDetails(note, name_of_trip);


            }
        });

    }

    private void startGoogleSearch(String isStartPoint) {
        searchLayout.setVisibility(View.VISIBLE);

        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        View root = autocompleteFragment.getView();
        root.post(new Runnable() {
            @Override
            public void run() {
                root.findViewById(R.id.places_autocomplete_search_input)
                        .performClick();
            }
        });

        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
               /*
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();

                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(Objects.requireNonNull(place.getPhotoMetadatas()).get(0))
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener(
                        new OnSuccessListener<FetchPhotoResponse>() {
                            @Override
                            public void onSuccess(FetchPhotoResponse response) {
                                Bitmap bitmap = response.getBitmap();
                                ((ImageView)findViewById(R.id.img)).setImageBitmap(bitmap);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                exception.printStackTrace();
                            }
                        });*/
                if (isStartPoint.equals("isStart")) {
                    edtStartPoint1.setText(place.getName());
                    if (place.getLatLng() != null) {
                        startPLongtuide = place.getLatLng().latitude;
                        startPattuide = place.getLatLng().longitude;
                     //   Log.d("locationm", "onPlaceSelected: "+String.valueOf(startPLongtuide));
                        //Log.d("locationm", "onPlaceSelected: "+String.valueOf(startPattuide));

                    }
                } else {
                    edEndPoint.setText(place.getName());
                    if (place.getLatLng() != null) {
                        endPattuide = place.getLatLng().latitude;
                        endPLongtudie = place.getLatLng().longitude;
                    }
                }


                //Log.d("locationm", "onPlaceSelected: "+place.getName());


            }

            @Override
            public void onError(@NonNull Status status) {
                searchLayout.setVisibility(View.GONE);
                // Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // اضافه تفاصيل الرحله للفايربيز
    private void uploadTripDetails(String note, String name_of_trip) {

        DatabaseReference database = instance.getDatabaseReference();
        String id = instance.getUserId();

        FirebaseDatabase database1 = FirebaseDatabase.getInstance();

        // hash map
        Trip trip = new Trip();

        trip.setTripName(edtTripName.getEditText().getText().toString());
        trip.setStartPoint(edtStartPoint1.getText().toString());
        trip.setEndPoint(edEndPoint.getText().toString());
        trip.setTime(date_picker_actions.getText().toString());
        trip.setDate(time_picker_actions.getText().toString());
        trip.setTripNotes(edtTripNote.getEditText().getText().toString());

        trip.setSetStartPLogntuide(String.valueOf(startPLongtuide));
        trip.setSetStartPattuide(String.valueOf(startPattuide));

        trip.setSetEndPattuide(String.valueOf(endPattuide));
        trip.setSetEndPLogntuide(String.valueOf(endPLongtudie));


        trip.setTripType(tripType);


        DatabaseReference reference = database1.getReference(id).child("trips");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                GenericTypeIndicator<List<Trip>> genericTypeIndicator = new GenericTypeIndicator<List<Trip>>() {
                };


                List<Trip> trips = (ArrayList<Trip>) dataSnapshot.getValue();
                // Check if friends list exists
                if (trips == null) {
                    // Create list
                    trips = new ArrayList<Trip>();
                }
                // Add new user
                // database1.getReference(id).child("trips").child(String.valueOf("0")).setValue(trips);


                if (trbName != null) {
                    Map<String, Object> postValues = new HashMap<>();
                    postValues.put("tripName", edtTripName.getEditText().getText().toString());
                    postValues.put("startPoint", edtStartPoint1.getText().toString());
                    postValues.put("endPoint", edEndPoint.getText().toString());
                    postValues.put("date", date_picker_actions.getText().toString());
                    postValues.put("time", time_picker_actions.getText().toString());
                    postValues.put("tripNotes", edtTripNote.getEditText().getText().toString());
                    postValues.put("tripType", tripType);
                    postValues.put("startPLongtuide", startPLongtuide);
                    postValues.put("startPattuide", startPattuide);
                    postValues.put("endPattuide", endPattuide);
                    postValues.put("endPLongtudie", endPLongtudie);


                    //trips.set(pos, trip);
                    database1.getReference(id).child("trips").child(String.valueOf(pos)).updateChildren(postValues);

                } else {
                    trips.add(trip);
                    database1.getReference(id).child("trips").setValue(trips);
                }
                // Set updated friends list

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        finish();
    }


    private void dateCalendar() {
        Calendar calendar = Calendar.getInstance();
        if (TripMonth == 1 || TripDay == 0 || TripYear == 0) {
            TripDay = calendar.get(Calendar.DAY_OF_MONTH);
            TripMonth = calendar.get(Calendar.MONTH);
            TripYear = calendar.get(Calendar.YEAR);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTrip.this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        TripMonth = datePicker.getMonth();
                        if (TripMonth >= 0)
                            TripMonth = TripMonth + 1;
                        TripDay = datePicker.getDayOfMonth();
                        TripYear = datePicker.getYear();
                        date_picker_actions.setText(TripYear + "/" + TripMonth + "/" + TripDay);
                    }
                }, TripYear, TripMonth, TripDay);




      /*  mDateListener = (datePicker, year1, month1, day1) -> {



        };
        datePicker*/
        datePickerDialog.show();
    }


    private void timePiker() {
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker = new TimePickerDialog(AddTrip.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new TimePickerDialog.OnTimeSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                time_picker_actions.setText(selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.trip_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.cancel_trip) {
            finish();
            Toast.makeText(this, "trip cancelled", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}