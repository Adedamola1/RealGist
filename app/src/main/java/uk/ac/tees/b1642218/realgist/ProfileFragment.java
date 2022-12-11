package uk.ac.tees.b1642218.realgist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import uk.ac.tees.b1642218.realgist.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {

    ImageView profile_selfie;
    TextView addImage;
    Button edit;
    TextInputLayout username, fullname, email, phoneNumber, address;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    private FragmentProfileBinding profileBinding;
    private View layout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        // Inflate the layout for this fragment

        db = FirebaseFirestore.getInstance();
        profileBinding = FragmentProfileBinding.inflate(getLayoutInflater(), container, false);

        if (!Places.isInitialized()) {
            Places.initialize(getContext(), "AIzaSyDYHH1pF9YMwYENCSZUD9wppQ4PaTp05pA");
        }

        initializeViews();


        profileBinding.address.setFocusable(false);
        profileBinding.address.setOnClickListener(v -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                    Place.Field.LAT_LNG, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList)
                    .build(getContext());
            startActivityForResult(intent, 100);

        });
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getContext());


        profileBinding.btnProfileEdit.setOnClickListener(this::editProfile);
        profileBinding.btnlogout.setOnClickListener(v -> {
            auth.signOut();
            Intent intent = new Intent(getActivity(), Login.class);
            startActivity(intent);
            getActivity().finish();
        });

        return profileBinding.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            profileBinding.address.setText(place.getAddress());
            Log.d("ADDRESS GOTTEN", "onActivityResult: " + place.getName());

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Toast.makeText(getContext(), status.getStatusMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initializeViews() {
        profileBinding.editProgressIndicator.setVisibility(View.VISIBLE);


        db.collection("users")
                .document(user.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    //Setting
                    profileBinding.txtUsername.setText(documentSnapshot.getString("Username"));
                    profileBinding.profileEmail.setText(documentSnapshot.getString("email"));
                    profileBinding.firstName.setText(documentSnapshot.getString("firstname"));
                    profileBinding.lastName.setText(documentSnapshot.getString("lastname"));
                    profileBinding.address.setText(documentSnapshot.getString("address"));


                    profileBinding.editProgressIndicator.setVisibility(View.INVISIBLE);
                    profileBinding.mainProfile.setVisibility(View.VISIBLE);
                });
    }

    private void editProfile(View v) {
        HashMap<String, Object> user_firebase = new HashMap<>();

        profileBinding.editProgressIndicator.setVisibility(View.VISIBLE);
        profileBinding.btnProfileEdit.setText("...Editing");
        profileBinding.btnProfileEdit.setClickable(false);

        user_firebase.put("Username", profileBinding.txtUsername.getText().toString());
        user_firebase.put("email", profileBinding.profileEmail.getText().toString());
        user_firebase.put("firstname", profileBinding.firstName.getText().toString());
        user_firebase.put("lastname", profileBinding.lastName.getText().toString());
        user_firebase.put("address", profileBinding.address.getText().toString());


        db.collection("users")
                .document(user.getUid())
                .update(user_firebase)
                .addOnSuccessListener(documentReference -> {

                    profileBinding.editProgressIndicator.setVisibility(View.INVISIBLE);
                    profileBinding.btnProfileEdit.setText("Edit");
                    profileBinding.btnProfileEdit.setClickable(true);

                })
                .addOnFailureListener(e -> {
                    profileBinding.editProgressIndicator.setVisibility(View.INVISIBLE);
                    profileBinding.btnProfileEdit.setText("Edit");
                    profileBinding.btnProfileEdit.setClickable(true);

                });

    }
}