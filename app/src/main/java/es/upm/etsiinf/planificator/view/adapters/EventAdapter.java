package es.upm.etsiinf.planificator.view.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

import es.upm.etsiinf.planificator.R;
import es.upm.etsiinf.planificator.model.Event;
import es.upm.etsiinf.planificator.view.EventDetails;

public class EventAdapter extends FirebaseRecyclerAdapter<Event,EventAdapter.EventViewHolder> {

    public static String urlmood;

    public EventAdapter(FirebaseRecyclerOptions<Event> options) {
        super(options);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent,false);
        return new EventViewHolder(layoutView);
    }

  //  @Override
    /*public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        final Event evento = eventList.get(holder.getAdapterPosition());
        holder.name.setText(evento.getName());
        holder.desc.setText(evento.getName());
        holder.time.setText(evento.getHour());

    }**/

    @Override
    protected void onBindViewHolder(@NonNull EventViewHolder holder, int position, @NonNull Event model) {
        holder.name.setText(model.getName());
        holder.desc.setText(model.getDescription());
        holder.time.setText(model.getHour());
        urlmood = model.getSurl();
        Glide.with(holder.mood.getContext())
                .load(model.getSurl())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.mood);
    }




    public static class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView desc;
        public TextView time;

        public ImageView mood;

        public ConstraintLayout cons;

        public EventViewHolder(@NonNull View itemView){
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.recyname);
            desc = (TextView) itemView.findViewById(R.id.recydesc);
            time = (TextView) itemView.findViewById(R.id.recytime);
            cons = (ConstraintLayout) itemView.findViewById(R.id.recycons);
            mood = (ImageView) itemView.findViewById(R.id.moodimage);

            cons.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(itemView.getContext(), EventDetails.class);
                    i.putExtra("name", name.getText().toString().trim());
                    i.putExtra("hour", time.getText().toString().trim());
                    i.putExtra("description",desc.getText().toString().trim());
                    i.putExtra("url",urlmood);
                    itemView.getContext().startActivity(i);
                }
            });
        }


    }


}
