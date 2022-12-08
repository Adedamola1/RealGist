package uk.ac.tees.b1642218.realgist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;

import uk.ac.tees.b1642218.realgist.databinding.ActivityEventDetailsBinding;
import uk.ac.tees.b1642218.realgist.recycler.Event;

public class EventDetails extends AppCompatActivity {
    Button attendEvent;
    FirebaseAuth auth;
    FirebaseUser user;
    private Event events;
    private ActivityEventDetailsBinding eventDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventDetailsBinding = ActivityEventDetailsBinding.inflate(getLayoutInflater());
        setContentView(eventDetailsBinding.getRoot());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        events = (Event) getIntent().getSerializableExtra("eventDetails");
        attendEvent = findViewById(R.id.ed_attend_button);

        Log.d("WORKS", "onCreate: " + events.getTitle());

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDensity = DisplayMetrics.DENSITY_XXHIGH;//for example

        loadData(options);

        attendEvent.setOnClickListener(v -> {
                    HashMap<String, Object> eventAttendee = new HashMap<>();
                    List<String> attendees = events.getAttendees();

                    db.collection("events")
                            .whereEqualTo("id", events.getId())
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        DocumentReference washingtonRef = db.collection("events").document(document.getId());
                                        washingtonRef.update("attendees", FieldValue.arrayUnion(user.getUid())).addOnCompleteListener(task1 -> {
                                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(this, EventFragment.class);
                                            startActivity(intent);
                                            finish();
                                        });
                                        Log.d("TAG", document.getId() + " => " + document.getData());
                                    }
                                }
                            });
                }
        );
    }

    private void loadData(BitmapFactory.Options options) {
        byte[] bytes = Base64.decode(events.getEventImage(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);

        eventDetailsBinding.edEventBanner.setImageBitmap(bitmap);
        eventDetailsBinding.edEventBanner.setScaleType(ImageView.ScaleType.CENTER_CROP);

//        citiesRef.whereArrayContains("regions", "west_coast");
        eventDetailsBinding.edEventTitle.setText(events.getTitle());
        eventDetailsBinding.edOverview.setText(events.getAbout());
        eventDetailsBinding.edCategory.setText(events.getCategory());
        eventDetailsBinding.edEventDate.setText(events.getDate());
        eventDetailsBinding.edEventTime.setText(events.getTime());

        Log.d("EVENT", "onCreate: " + events.getAttendees());
//        eventDetailsBinding.edAttendButton.setText(events.getPeopleAttending());

        String people = "";
        if (events.getAttendees().size() == 0) {
            people = "O person attending";
        }
        people = events.getAttendees().size() + " person attending";

        eventDetailsBinding.edEventAtending.setText(people);
        eventDetailsBinding.edLocation.setText(events.getVenue());

        if (events.getAttendees().contains(user.getUid())) {
            eventDetailsBinding.edAttendButton.setText("Attending Event");
            eventDetailsBinding.edAttendButton.setEnabled(false);

        }
    }


}