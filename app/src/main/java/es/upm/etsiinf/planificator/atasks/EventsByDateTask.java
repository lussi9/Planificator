package es.upm.etsiinf.planificator.atasks;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import es.upm.etsiinf.planificator.R;
import es.upm.etsiinf.planificator.model.Event;
import es.upm.etsiinf.planificator.view.adapters.EventAdapter;

public class EventsByDateTask extends AsyncTask<Void, Void, FirebaseRecyclerAdapter<Event, EventAdapter.EventViewHolder>> {

    public static String urlmoo;
    private RecyclerView recyclerView;
    private DatabaseReference dbref;
    private String date;

    public EventsByDateTask(RecyclerView re, String seldate) {
        this.recyclerView = re;
        this.date = seldate;

        dbref = FirebaseDatabase
                .getInstance("https://planificator-d3acf-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference().child("events");
    }

    @Override
    protected FirebaseRecyclerAdapter<Event, EventAdapter.EventViewHolder> doInBackground(Void... voids) {
        // Query to fetch data from the database for the selected date
        Query query = dbref.orderByChild("date").equalTo(date);

        // Configure options for the adapter
        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(query, Event.class)
                        .build();
        return new FirebaseRecyclerAdapter<Event, EventAdapter.EventViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position, @NonNull Event model) {
                holder.name.setText(model.getName());
                holder.desc.setText(model.getDescription());
                holder.time.setText(model.getHour());
                urlmoo = model.getSurl();
                Glide.with(holder.mood.getContext())
                        .load(model.getSurl())
                        .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                        .circleCrop()
                        .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                        .into(holder.mood);
            }

            @NonNull
            @Override
            public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                @SuppressLint("InflateParams") View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
                return new EventAdapter.EventViewHolder(layoutView);
            }
        };


    }

    @Override
    protected void onPostExecute(FirebaseRecyclerAdapter<Event, EventAdapter.EventViewHolder> adapter) {
        // Set the adapter to your RecyclerView
        recyclerView.setAdapter(adapter);

        // Start listening for data changes
        adapter.startListening();
    }
}
