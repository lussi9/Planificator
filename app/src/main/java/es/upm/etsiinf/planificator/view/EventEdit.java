package es.upm.etsiinf.planificator.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.database.FirebaseDatabase;

import es.upm.etsiinf.planificator.R;
import es.upm.etsiinf.planificator.model.Event;

public class EventEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventoedit);


        Button saveb = findViewById(R.id.button_save);
        EditText name = findViewById(R.id.editname);
        EditText date = findViewById(R.id.editdate);
        EditText hour = findViewById(R.id.edithour);
        EditText desc = findViewById(R.id.editdesc);
        Spinner mood = (Spinner) findViewById(R.id.moodspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.moods,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mood.setAdapter(adapter);

        saveb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event eventonuevo = new Event(name.getText().toString(),date.getText().toString(),hour.getText().toString(),desc.getText().toString());
                FirebaseDatabase.getInstance("https://planificator-d3acf-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("events").push().setValue(eventonuevo);

            }
        });
    }




}