package es.upm.etsiinf.planificator.view.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.upm.etsiinf.planificator.R;
import es.upm.etsiinf.planificator.model.Event;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    List<Event> eventList;

    public void setData(List<Event> data){
        this.eventList = data;
    }
    public EventAdapter(List<Event> listdetails) {
        this.eventList = listdetails;
    }


    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, null);
        return new EventViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        final Event evento = eventList.get(holder.getAdapterPosition());
        holder.name.setText(evento.getName());
        holder.desc.setText(evento.getName());
        holder.time.setText(evento.getHour());

    }

    @Override
    public int getItemCount() {
        return this.eventList.size();
    }

    public long getItemId(int position){
        return super.getItemId(position);
    }

    public Event getItem(int position){
        if(position!=-1){
            return eventList.get(position);
        }
        else return null;
    }

    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView name;
        private final TextView desc;
        private final TextView time;

        EventViewHolder(View itemView){
            super(itemView);
            ConstraintLayout co = itemView.findViewById(R.id.recycons);
            name = (TextView) co.findViewById(R.id.recyname);
            desc = (TextView) co.findViewById(R.id.recydesc);
            time = (TextView) co.findViewById(R.id.recytime);
            co.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.recycons){

            }

        }
    }


}
