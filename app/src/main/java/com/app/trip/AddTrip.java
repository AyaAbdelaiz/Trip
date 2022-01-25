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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddTrip extends AppCompatActivity {

    TextView date_picker_actions, time_picker_actions;
    Toolbar toolbar;
    TextInputLayout edtTripNote, edtTripName, edtStartPoint1, edEndPoint;
    // TextInputLayout edtStartPoint1;
    Button addTrip;
    String note, name_of_trip;
    FirebaseInstance instance;
    RadioGroup radioGroup;
    String tripType = "";
    int TripMonth = 1, TripDay = 0, TripYear = 0;


    String placesApiToken = "AIzaSyAzyToZ95jgePDTBDOZc5wNqXeihsJ31eg";

    PlacesClient placesClient;

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

        radioGroup = (RadioGroup) findViewById(R.id.rdGroup);

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
        edtStartPoint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                        getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
                autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
                autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS));
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
                    }

                    @Override
                    public void onError(@NonNull Status status) {
                        Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        // getActionBar().setTitle("Add trip");

        instance = new FirebaseInstance();

        date_picker_actions = findViewById(R.id.date_picker_actions);
        time_picker_actions = findViewById(R.id.time_picker_actions);
        addTrip = findViewById(R.id.btnAdd);
        date_picker_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateCalendar();
            }
        });

        time_picker_actions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePiker();
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();


        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name_of_trip = edtTripName.getEditText().getText().toString();
                note = edtTripNote.getEditText().getText().toString();
                uploadTripDetails(note, name_of_trip);


            }
        });

    }

    // اضافه تفاصيل الرحله للفايربيز
    private void uploadTripDetails(String note, String name_of_trip) {

        try {
            DatabaseReference database = instance.getDatabaseReference();
            String id = instance.getUserId();

            // hash map
            Map<String, Object> map = new HashMap<>();
            map.put("Name", edtTripName.getEditText().getText().toString());
            map.put("startPoint", edtStartPoint1.getEditText().getText().toString());
            map.put("endPoint", edEndPoint.getEditText().getText().toString());
            map.put("time", date_picker_actions.getText().toString());
            map.put("date", time_picker_actions.getText().toString());
            map.put("tripType",tripType);

            FirebaseDatabase.getInstance().getReference().child("usersTrip").push()
                    .setValue(map);


          /*  reference.child("name").setValue(name_of_trip);
            reference.child("startPointTrip").setValue("cairo");
            reference.child("endPointTrip").setValue("giza");
            reference.child("date").setValue(date_picker_actions.getText());
            reference.child("time").setValue(time_picker_actions.getText());
            reference.child("notes").setValue(note);
*/
        } catch (Exception ex) {
            Log.d("ayaaa", "ert" + ex.getMessage());

        }
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