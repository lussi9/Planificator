package es.upm.etsiinf.planificator.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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

    private EditText name, date, hour, desc;
    private Button saveb;

    private String item, surl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventoedit);


        saveb = findViewById(R.id.button_save);
        name = findViewById(R.id.editname);
        date = findViewById(R.id.editdate);
        hour = findViewById(R.id.edithour);
        desc = findViewById(R.id.editdesc);

        Bundle bundle = getIntent().getExtras();
        String fechaa = bundle.getString("fecha");
        date.setText(fechaa,TextView.BufferType.EDITABLE);

        name.addTextChangedListener(fillWatcher);
        date.addTextChangedListener(fillWatcher);
        hour.addTextChangedListener(fillWatcher);
        desc.addTextChangedListener(fillWatcher);

        Spinner mood = (Spinner) findViewById(R.id.moodspinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.moods,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mood.setAdapter(adapter);
        mood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getItemAtPosition(i).toString().equals("Mood")){
                    item=adapterView.getItemAtPosition(i).toString();
                }
                else {
                    item = adapterView.getItemAtPosition(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                item= "Mood";
            }
        });


        saveb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(item){
                    case "Happy": surl= "https://emojiapi.dev/api/v1/grinning_face_with_smiling_eyes/128.png"; break;
                    case "Angry": surl="https://emojiapi.dev/api/v1/pouting_face/128.png"; break;
                    case "Serious": surl="https://emojiapi.dev/api/v1/confused_face/128.png";break;
                    case "None": surl="https://emojiapi.dev/api/v1/neutral_face/128.png";break;
                    case "Mood":surl="https://emojiapi.dev/api/v1/aquarius/128.png";break;
                    default: surl="https://emojiapi.dev/api/v1/wavy_dash/128.png";break;
                }

                Event eventonuevo = new Event(name.getText().toString(),date.getText().toString(),hour.getText().toString(),desc.getText().toString(),surl);
                FirebaseDatabase.getInstance("https://planificator-d3acf-default-rtdb.europe-west1.firebasedatabase.app/").getReference().child("events").push().setValue(eventonuevo);
                sendNotification(view);
                finish();
            }
        });
    }

    public void sendNotification(View view) {
        // Create a notification manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Define a notification channel for devices running Android 8.0 (Oreo) and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "MyChannelId";
            CharSequence channelName = "MyChannelName";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.enableLights(true);
            channel.setLightColor(Color.YELLOW);

            notificationManager.createNotificationChannel(channel);
        }

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MyChannelId")
                .setSmallIcon(R.drawable.plan_noticon) // You should have an icon in the 'res/drawable' folder
                .setContentTitle("Event created")
                .setContentText(name.getText().toString().trim())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        notificationManager.notify(1, builder.build());
    }

    private TextWatcher fillWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String nameinput = name.getText().toString().trim();
            String dateinput = date.getText().toString().trim();
            String hourinput = hour.getText().toString().trim();
            String descinput = desc.getText().toString().trim();


            saveb.setEnabled(!nameinput.isEmpty() && !dateinput.isEmpty() && !hourinput.isEmpty()&& !descinput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

}