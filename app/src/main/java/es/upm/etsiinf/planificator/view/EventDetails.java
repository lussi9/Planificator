package es.upm.etsiinf.planificator.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import es.upm.etsiinf.planificator.R;
import es.upm.etsiinf.planificator.atasks.EventsByDateTask;
import es.upm.etsiinf.planificator.view.adapters.EventAdapter;

public class EventDetails extends AppCompatActivity {
    public static Bitmap bitmap;
    public ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        TextView name=(TextView)findViewById(R.id.eventname);
        TextView date=(TextView)findViewById(R.id.datetext);
        TextView hour=(TextView)findViewById(R.id.hourtext);
        TextView desc=(TextView)findViewById(R.id.desctext);
        image = findViewById(R.id.imagedown);
        Glide.with(this)
                .load(EventsByDateTask.urlmoo)
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(image);




        name.setText(getIntent().getStringExtra("name"));
        date.setText(CalendarActivity.fechacalendario);
        hour.setText(getIntent().getStringExtra("hour"));
        desc.setText(getIntent().getStringExtra("description"));
        FloatingActionButton but = (FloatingActionButton) findViewById(R.id.sharebutton);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Body="Event: "+name.getText().toString().trim();
                String Sub="date: "+date.getText().toString().trim()+", Hour: "+hour.getText().toString().trim();
                intent.putExtra(Intent.EXTRA_TEXT,Body+", "+Sub);
                intent.putExtra(Intent.EXTRA_SUBJECT,Sub);
                startActivity(Intent.createChooser(intent,"Share event with"));

            }
        });

    }




}