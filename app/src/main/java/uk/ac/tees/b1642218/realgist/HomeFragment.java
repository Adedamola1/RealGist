package uk.ac.tees.b1642218.realgist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import uk.ac.tees.b1642218.realgist.recycler.Event;
import uk.ac.tees.b1642218.realgist.recycler.EventAdapter;
import uk.ac.tees.b1642218.realgist.recycler.EventListener;

public class HomeFragment extends Fragment implements EventListener {

    DatabaseReference dbEvents;
    FirebaseAuth auth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    EventAdapter eventAdapter;
    FirebaseUser user;
    ArrayList<Event> events;
    ProgressDialog progressDialog;
    View layout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_home, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching events...");

        progressDialog.show();

        CollectionReference eventList = db.collection("events");
        events = new ArrayList<>();

        RecyclerView eventRC = layout.findViewById(R.id.event_card_rc);
        eventRC.setHasFixedSize(true);
        eventRC.setLayoutManager(new LinearLayoutManager(getContext()));
        eventAdapter = new EventAdapter(events, getActivity().getApplicationContext(), this);
        eventRC.setAdapter(eventAdapter);

        eventChangeListener();
        return layout;
    }

    private void eventChangeListener() {
        Log.d("FIREBASE", "eventChangeListener: " + user.getUid());
        db.collection("events").whereNotEqualTo("creatorID", user.getEmail()).addSnapshotListener((value, error) -> {
            if (error != null) {

                if (progressDialog.isShowing()) progressDialog.dismiss();
                Log.d("FETCHING", "error" + error.getMessage());
                return;
            }

            for (DocumentChange document :
                    value.getDocumentChanges()) {
                if (document.getType() == DocumentChange.Type.ADDED) {
                    events.add(document.getDocument().toObject(Event.class));
                }

                eventAdapter.notifyDataSetChanged();
                if (progressDialog.isShowing()) progressDialog.dismiss();
            }


        });
    }


    @Override
    public void onEventClicked(Event event) {
        Log.d("EVENT", "Event clicked" + event.getTitle());
        Intent intent = new Intent(getActivity(), EventDetails.class);
        intent.putExtra("eventDetails", event);
        startActivity(intent);
    }
}