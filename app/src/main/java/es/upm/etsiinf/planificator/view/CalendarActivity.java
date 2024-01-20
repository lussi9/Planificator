package es.upm.etsiinf.planificator.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

import es.upm.etsiinf.planificator.R;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        CalendarView calendario = findViewById(R.id.calendarView);
        final TextView fechaact = findViewById(R.id.fecha_actual);
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String ho = day + " " + Month.of(month+1).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
                fechaact.setText(ho);

            }
        });
        FloatingActionButton botoon = findViewById(R.id.floatingActionButton);
        botoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalendarActivity.this, EventEdit.class);
                startActivity(intent);
            }
        });


    }
}