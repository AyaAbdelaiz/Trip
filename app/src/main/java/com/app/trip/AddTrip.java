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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class AddTrip extends AppCompatActivity {

    TextView date_picker_actions, time_picker_actions;
    Toolbar toolbar;
    TextInputLayout edtTripNote,edtTripName;
    Button addTrip;
    String note,name_of_trip;
    FirebaseInstance instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        toolbar = findViewById(R.id.trip_toolbar);
        setSupportActionBar(toolbar);
        edtTripNote=findViewById(R.id.edtTripNote);
        edtTripName=findViewById(R.id.edtTripName);


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
                name_of_trip=edtTripName.getEditText().getText().toString();
                note=edtTripNote.getEditText().getText().toString();
                uploadTripDetails(note,name_of_trip);
                Toast.makeText(AddTrip.this, ""+note, Toast.LENGTH_SHORT).show();


            }
        });

    }

    private void uploadTripDetails(String note,String name_of_trip) {

        try {
            DatabaseReference database = instance.getDatabaseReference();
            String id = instance.getUserId();
            database.child(id).child("name").setValue(name_of_trip);
            database.child(id).child("startPointTrip").setValue("cairo");
            database.child(id).child("endPointTrip").setValue("giza");
            database.child(id).child("date").setValue(date_picker_actions.getText());
            database.child(id).child("time").setValue(time_picker_actions.getText());
            database.child(id).child("notes").setValue(note);

        } catch (Exception ex) {
            Log.d("ayaaa", "ert" + ex.getMessage());

        }
    }

    private void dateCalendar() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTrip.this,
                AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        i = i + 1;
                        date_picker_actions.setText(i + "/" + i1 + "/" + i2);
                    }
                }, year, month, day);

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