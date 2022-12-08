package uk.ac.tees.b1642218.realgist.recycler;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import uk.ac.tees.b1642218.realgist.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventHolder> {


    Context context;
    private ArrayList<Event> events;
    private EventListener eventListener;


    public EventAdapter(ArrayList<Event> events, Context context, EventListener eventListener) {
        this.context = context;
        this.events = events;
        this.eventListener = eventListener;

    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new EventHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder holder, int position) {
        Event event = events.get(position);

        holder.bindEvent(events.get(position));

    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    class EventHolder extends RecyclerView.ViewHolder {

        ImageView eventBanner;
        LinearLayout eventCardLayout;
        TextView eventDate, eventTitle, eventLocation, eventPeople;
        View eventView;


        public EventHolder(@NonNull View view) {
            super(view);
            eventView = view;
            eventCardLayout = view.findViewById(R.id.eventCardLayout);
            eventBanner = view.findViewById(R.id.event_banner);
            eventDate = view.findViewById(R.id.eventDate);
            eventTitle = view.findViewById(R.id.eventTitle);
            eventLocation = view.findViewById(R.id.location);
            eventPeople = view.findViewById(R.id.event_atending);


        }

        void bindEvent(Event events) {


            Log.d("ATTEND", "bindEvent: " + events.getAttendees().size());

            byte[] bytes = Base64.decode(events.getEventImage(), Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            eventBanner.setImageBitmap(bitmap);
            eventBanner.setScaleType(ImageView.ScaleType.FIT_XY);

            eventDate.setText(events.getDate());
            eventTitle.setText(events.getTitle());
            eventLocation.setText(events.getVenue());

            eventView.setOnClickListener(v -> {
                eventListener.onEventClicked(events);
            });


            String people = "";
            if (events.getAttendees().size() == 0) {
                people = "O person attending";
            }
            people = events.getAttendees().size() + " person(s) attending";
            eventPeople.setText(people);
        }
    }
}
