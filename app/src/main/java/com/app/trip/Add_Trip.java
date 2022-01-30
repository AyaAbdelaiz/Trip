package com.app.trip;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;

import java.util.ArrayList;
import java.util.Calendar;
import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Add_Trip extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    static final int PLACE_AUTOCOMPLETE_START_PLACE_REQUEST_CODE = 1;
    static final int PLACE_AUTOCOMPLETE_END_PLACE_REQUEST_CODE = 2;
   TripRoomDatabase  tripRoomDatabase;
    ImageButton datePickerBtn;
    ImageButton timePickerBtn;
    EditText startPoint;
    EditText endPoint;
    EditText tripName;
    TextView timePicked;
    TextView datePicked;
    Spinner tripTypes;
    Button addTrip;
    int year,month,day;
    boolean datePickedCheck = false;
    static String TAG="main";
    Intent intent;
    PendingIntent pendingIntent;
    Intent intentgoing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        initComponent();
        initDropDownList();
        initGooglePlaces();

        addTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tripName.getText().toString().isEmpty() || startPoint.getText().toString().isEmpty()
                        || endPoint.getText().toString().isEmpty()|| timePicked.getText().toString().isEmpty()
                       || datePicked.getText().toString().isEmpty()) {
                    Toast.makeText(Add_Trip.this, "Please fill all fields to add trip", Toast.LENGTH_LONG).show();
                }
               else{

                   tripRoomDatabase=TripRoomDatabase.getInstance(Add_Trip.this);

                    Trips trip = new Trips();
                  //  trip.setUserID(FirebaseAuth.getInstance().getUid());
                    trip.setTripName(tripName.getText().toString());
                    trip.setFrom(startPoint.getText().toString());
                    trip.setTo(endPoint.getText().toString());
                    trip.setDate(datePicked.getText().toString());
                    trip.setTime(timePicked.getText().toString());
                    trip.setTripStatus("Upcoming");
                    trip.setType(tripTypes.getSelectedItem().toString());
                    //trip.setNotes(new ArrayList<>());
                    trip.setId((int) Calendar.getInstance().getTimeInMillis());

                 tripRoomDatabase.tripDao().insert(trip).subscribeOn(Schedulers.io())
                         .subscribe(new CompletableObserver(){


                             @Override
                             public void onSubscribe(@NonNull Disposable d) {

                             }

                             @Override
                             public void onComplete() {
                                 Log.i(TAG, "onComplete: "+trip.getTime());


                               //  alarmHelper.addAlarm(trip);
                                // finish();
                             }

                             @Override
                             public void onError(@NonNull Throwable e) {

                             }
                         });

                }

            }
        });
       /* if (tripTypes.getSelectedItem().toString().equals("Round trip"))
        {
            tripRoomDatabase = TripRoomDatabase.getInstance(Add_Trip.this);
            Trips tripBack = new Trips();
           // tripBack.setUserID(FirebaseAuth.getInstance().getUid());
            tripBack.setTripName(tripName.getText().toString());
            tripBack.setFrom(endPoint.getText().toString());
            tripBack.setTo(startPoint.getText().toString());
            tripBack.setTripStatus("Upcoming");
            tripBack.setType(tripTypes.getSelectedItem().toString());
           // tripBack.setNotes(new ArrayList<>());

            tripRoomDatabase.tripDao().insert(tripBack).subscribeOn(Schedulers.io())
                    .subscribe(new CompletableObserver() {
                        @Override
                        public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                        }

                        @Override
                        public void onComplete() {
                            finish();
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                        }
                    });*/
        //}

    }






    private void initComponent(){
        datePickerBtn=findViewById(R.id.imageDate);
        timePickerBtn=findViewById(R.id.imageTime);
        startPoint=findViewById(R.id.edtStartPoint);
        endPoint=findViewById(R.id.edtEndPoint);
        tripName=findViewById(R.id.edtTripName);
        timePicked=findViewById(R.id.txtTime);
        datePicked=findViewById(R.id.txtDate);
        tripTypes=findViewById(R.id.spinner1);
        addTrip=findViewById(R.id.btnAdd);


    }


    private void initGooglePlaces() {
        Places.initialize(getApplicationContext(), "AIzaSyAgqIxtaivfYwAkTXbID2Ew1hra3Exy7Rg");
        startPoint.setFocusable(false);
        endPoint.setFocusable(false);
    }
    private void initDropDownList() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.trip_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tripTypes.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK)
        {
            Place place = Autocomplete.getPlaceFromIntent(data);
            if(requestCode== PLACE_AUTOCOMPLETE_START_PLACE_REQUEST_CODE)
                startPoint.setText(place.getAddress());
            else if (requestCode== PLACE_AUTOCOMPLETE_END_PLACE_REQUEST_CODE)
                endPoint.setText(place.getAddress());
        }
        else {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(this, status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }

//////////////////////
@Override
public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
    Calendar calendar = Calendar.getInstance();

    if( (day == calendar.get(Calendar.DAY_OF_MONTH) && month == calendar.get(Calendar.MONTH) && year == calendar.get(Calendar.YEAR))
            &&
            hourOfDay <= calendar.get(Calendar.HOUR_OF_DAY)  && minute <= calendar.get(Calendar.MINUTE)) {
        Toast.makeText(Add_Trip.this, "This time has already passed, please choose a different time", Toast.LENGTH_LONG).show();
    }
    else {
        String AM_PM = " AM";
        String mm_precede = "";
        if (hourOfDay >= 12) {
            AM_PM = " PM";
            if (hourOfDay >= 13 && hourOfDay < 24) {
                hourOfDay -= 12;
            } else {
                hourOfDay = 12;
            }
        } else if (hourOfDay == 0) {
            hourOfDay = 12;
        }
        if (minute < 10) {
            mm_precede = "0";
        }
        timePicked.setText("" + hourOfDay + ":" + mm_precede + minute + AM_PM);
    }
}



////////////////////////////

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
        datePicked.setText(dayOfMonth +"/"+(month+1)+"/"+year);
        timePicked.setText("");
        datePickedCheck = true;
    }}