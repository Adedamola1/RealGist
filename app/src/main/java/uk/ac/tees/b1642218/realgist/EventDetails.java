package uk.ac.tees.b1642218.realgist;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.b1642218.realgist.databinding.ActivityEventDetailsBinding;
import uk.ac.tees.b1642218.realgist.recycler.Event;

public class EventDetails extends AppCompatActivity {
    private Event events;

    private ActivityEventDetailsBinding eventDetailsBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventDetailsBinding = ActivityEventDetailsBinding.inflate(getLayoutInflater());
        setContentView(eventDetailsBinding.getRoot());

        events = (Event) getIntent().getSerializableExtra("eventDetails");

        Log.d("WORKS", "onCreate: " + events.getTitle());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = DisplayMetrics.DENSITY_XXHIGH;//for example

        byte[] bytes = Base64.decode(events.getEventImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        
        eventDetailsBinding.edEventBanner.setImageBitmap(bitmap);
        eventDetailsBinding.edEventBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);

        eventDetailsBinding.edEventTitle.setText(events.getTitle());
        eventDetailsBinding.edOverview.setText(events.getAbout());
        eventDetailsBinding.edCategory.setText(events.getCategory());
        eventDetailsBinding.edEventDate.setText(events.getDate());
        eventDetailsBinding.edEventTime.setText(events.getTime());

        String people = "";
        if (events.getPeopleAttending() == 0) {
            people = "O person attending";
        }
        people = events.getPeopleAttending() + " person attending";

        eventDetailsBinding.edEventAtending.setText(people);
        eventDetailsBinding.edLocation.setText(events.getVenue());
    }

}