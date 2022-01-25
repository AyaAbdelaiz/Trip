package com.app.trip;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class AddNotes extends AppCompatActivity {
    public static String TAG = "main";
    List<String> notes;
    RecyclerView recyclerView;
    NotesAdapter myAdapter;
    Button btn;
    TextView txt;
    Button btnSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);
        btn=findViewById(R.id.add_note_btn);
        txt=findViewById(R.id.note_txt);
        btnSave=findViewById(R.id.save_notes);
        recyclerView=findViewById(R.id.notes_recycler);
        Intent intent=getIntent();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager LayoutManager=new LinearLayoutManager(this);
        LayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(LayoutManager);
        myAdapter=new NotesAdapter(AddNotes.this,notes);
        recyclerView.setAdapter(myAdapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=txt.getText().toString();
                if(text.length()>0){
                    notes.add(text);
                    txt.setText("");
                    myAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}