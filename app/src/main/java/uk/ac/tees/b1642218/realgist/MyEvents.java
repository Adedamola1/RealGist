package uk.ac.tees.b1642218.realgist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import uk.ac.tees.b1642218.realgist.recycler.Event;
import uk.ac.tees.b1642218.realgist.recycler.EventAdapter;

public class MyEvents extends AppCompatActivity {

    Button btnMyEvents;
    FirebaseAuth auth;
    FirebaseUser user;
    ProgressDialog progressDialog;
    EventAdapter eventAdapter;
    RecyclerView favouriteRecycler;
    ArrayList<Event> events;
    TextView tv;

    View vinflate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_events);
        tv = vinflate.findViewById(R.id.no_event);

    }

    private void eventChangeListener() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching events...");

        progressDialog.show();

        Log.d("FIREBASE", "eventChangeListener: " + user.getUid());

        db.collection("events").whereArrayContains("attendees", user.getUid()).addSnapshotListener((value, error) -> {
            //db.collection("events").whereNotEqualTo("creatorID", user.getEmail()).addSnapshotListener((value, error) -> {
            if (error != null) {

                if (progressDialog.isShowing()) progressDialog.dismiss();
                //Log.d("FETCHING", "error" + error.getMessage());
                return;
            }

            for (DocumentChange document :
                    value.getDocumentChanges()) {
                if (document.getType() == DocumentChange.Type.ADDED) {
                    events.add(document.getDocument().toObject(Event.class));
                }

                eventAdapter.notifyDataSetChanged();

                //if (progressDialog.isShowing()) progressDialog.dismiss();
            }
            Log.d("TAG", "eventChangeListener: " + value.getDocumentChanges().size());
            if (eventAdapter.getItemCount() > 0) {
                favouriteRecycler.setVisibility(View.VISIBLE);
                tv.setVisibility(View.INVISIBLE);
            } else {
                favouriteRecycler.setVisibility(View.INVISIBLE);
                tv.setVisibility(View.VISIBLE);
            }
            if (progressDialog.isShowing()) progressDialog.dismiss();


        });

        btnMyEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyEvents.class);
                startActivity(intent);

            }
        });
    }

}