package es.upm.etsiinf.planificator.view;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import es.upm.etsiinf.planificator.R;
import es.upm.etsiinf.planificator.atasks.EventsByDateTask;
import es.upm.etsiinf.planificator.connection.CheckConnection;
import es.upm.etsiinf.planificator.model.Event;
import es.upm.etsiinf.planificator.view.adapters.EventAdapter;

public class CalendarActivity extends AppCompatActivity {
    public EventAdapter eve;
    public RecyclerView recy;
    public static String fechacalendario;

    public EventsByDateTask tareaasincrona;

    public static final String TAG = "CalendarActivity";

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    Log.w(TAG, "Notificaciones deshabilitadas");
                    // TODO: Inform user that that your app will not show notifications.
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        CalendarView calendario = findViewById(R.id.calendarView);
        final TextView fechaact = findViewById(R.id.fecha_actual);

        fechacalendario ="1/1/2024";

        recy = (RecyclerView)findViewById(R.id.recyclerView);
        recy.setLayoutManager(new LinearLayoutManager(this));
/*        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(FirebaseDatabase.getInstance("https://planificator-d3acf-default-rtdb.europe-west1.firebasedatabase.app/")
                                .getReference().child("events").orderByChild("date").equalTo("12/1/2024"), Event.class)
                        .build();

        eve = new EventAdapter(options);
        recy.setAdapter(eve);*/
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                String ho = day + " " + Month.of(month+1).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
                fechaact.setText(ho);
                fechacalendario =day+"/"+(month+1)+"/"+year;
                updateRecyclerViewForSelectedDate(fechacalendario);
/*                FirebaseRecyclerOptions<Event> options =
                        new FirebaseRecyclerOptions.Builder<Event>()
                                .setQuery(FirebaseDatabase.getInstance("https://planificator-d3acf-default-rtdb.europe-west1.firebasedatabase.app/")
                                        .getReference().child("events").orderByChild("date").equalTo(day+"/"+(month+1)+"/"+year), Event.class)
                                .build();

                eve = new EventAdapter(options);
                recy.setAdapter(eve);
                eve.startListening();*/

            }
        });



        FloatingActionButton botoon = findViewById(R.id.floatingActionButton);
        botoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CalendarActivity.this, EventEdit.class);
                Bundle bundle = new Bundle();
                bundle.putString("fecha", fechacalendario);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
        askNotificationPermission();

    }

    private void updateRecyclerViewForSelectedDate(String selectedDate) {
        // Format the selected date to match your database structure

        // Now, you can pass the formattedDate to your MyAsyncTask
        // You may need to modify MyAsyncTask to accept the selected date as a parameter
        tareaasincrona = new EventsByDateTask(recy, selectedDate);
        tareaasincrona.execute();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Stop listening for data changes when the activity is destroyed
        if (tareaasincrona != null) {
            FirebaseRecyclerAdapter<Event, EventAdapter.EventViewHolder> adapter = null;
            try {
                adapter = tareaasincrona.get();
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (adapter != null) {
                adapter.stopListening();
            }
        }
    }



    private void askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                Toast.makeText(this, "No llegaran notificaciones",Toast.LENGTH_SHORT).show();
            } else {
                // Directly ask for the permission
                requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS},200);
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
/*    @Override protected void onStart()
    {
        super.onStart();
        eve.startListening();
    }*/
}