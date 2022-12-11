package uk.ac.tees.b1642218.realgist;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfile extends AppCompatActivity {

    private static final String USERS = "users";
    Button edit;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    FirebaseDatabase db;
    ImageView profile_selfie;
    TextView fullName, username, userEmail, phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(USERS);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("email").getValue().equals(userEmail)) {
                        fullName.setText(ds.child("firstname").getValue(String.class) + " " + ds.child("Lastname").getValue(String.class));
                        username.setText(ds.child("username").getValue(String.class));
                        userEmail.setText(ds.child("email").getValue(String.class));
                        phoneNo.setText(ds.child("phoneNo").getValue(String.class));

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        profile_selfie = findViewById(R.id.profile_selfie);
//        fullName = findViewById(R.id.fullName);
//        username = findViewById(R.id.username);
//        userEmail = findViewById(R.id.email);
//        phoneNo = findViewById(R.id.phoneNo);
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inDensity = DisplayMetrics.DENSITY_XXHIGH;//for example
//
//        loadData(options);
//    }
//
//    private void loadData(BitmapFactory.Options options) {
//
//    }
//
//    public void btnRegisterUser(View view) {
//
//
    }
}