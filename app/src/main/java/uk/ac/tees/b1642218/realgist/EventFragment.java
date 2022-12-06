package uk.ac.tees.b1642218.realgist;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;


public class EventFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ImageView bannerImage;
    TextView bannerText;
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == getActivity().RESULT_OK) {
                    if (result.getData() != null) {
                        Uri imageUri = result.getData().getData();

                        InputStream inputStream = null;
                        try {
                            inputStream = getContext().getContentResolver().openInputStream(imageUri);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        bannerImage.setImageURI(result.getData().getData());
                        bannerImage.setImageBitmap(bitmap);
                        bannerText.setVisibility(View.GONE);
                        encodeImage = encodeImage(bitmap);


                    }
                }
            }
    );
    FirebaseAuth auth;
    FirebaseUser user;
    String locationName, latLong, encodeImage, category, timeText, dateText;
    EditText venue;
    TextInputLayout eventDesc, eventTitle;
    Button createEvent;
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
        layout = inflater.inflate(R.layout.fragment_event, container, false);
        Button datepicker = layout.findViewById(R.id.pick_date_btn);
        Button timeButton = layout.findViewById(R.id.pick_time_button);
        createEvent = layout.findViewById(R.id.btnCreateEvent);
        TextView date = layout.findViewById(R.id.show_selected_date);
        TextView time = layout.findViewById(R.id.time_result_view);
        bannerText = layout.findViewById(R.id.add_event_banner);


        venue = layout.findViewById(R.id.venue);
        eventDesc = layout.findViewById(R.id.event_description);
        eventTitle = layout.findViewById(R.id.event_title);

        FrameLayout imageLayout = layout.findViewById(R.id.layout_image);
        bannerImage = layout.findViewById(R.id.event_banner);


        if (!Places.isInitialized()) {
            Places.initialize(getContext(), "AIzaSyDYHH1pF9YMwYENCSZUD9wppQ4PaTp05pA");
        }

        venue.setFocusable(false);
        venue.setOnClickListener(v -> {
            List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS,
                    Place.Field.LAT_LNG, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fieldList)
                    .build(getContext());
            startActivityForResult(intent, 100);
            Log.d("woooow", "onCreateView: ");
        });


        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(getContext());

        MaterialDatePicker picker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Time")
                .build();

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show(getActivity().getSupportFragmentManager(), "Material_Date_Picker");
                picker.addOnPositiveButtonClickListener(selection -> {
                    date.setText(picker.getHeaderText());
                    dateText = picker.getHeaderText();
                });
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show(getActivity().getSupportFragmentManager(), "Material_Time_Picker");
                timePicker.addOnPositiveButtonClickListener(selection -> {
                    time.setText(String.format("%dh:%dmin", timePicker.getHour(), timePicker.getMinute()));
                    timeText = String.format("%dh:%dmin", timePicker.getHour(), timePicker.getMinute());
                });
            }
        });

        imageLayout.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, 101);
        });

        final Spinner spinner = (Spinner) layout.findViewById(R.id.category_spinner);
        createDropdown(spinner);


        String eventdesc = eventDesc.getEditText().getText().toString();
        String eventtitle = eventTitle.getEditText().getText().toString();


        createEvent.setOnClickListener(v -> {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            HashMap<String, Object> createdEvent = new HashMap<>();

            createdEvent.put("eventImage", encodeImage);
            createdEvent.put("creatorID", String.valueOf(user.getEmail()));
            createdEvent.put("creatorName", user.getDisplayName());
            createdEvent.put("title", eventTitle.getEditText().getText().toString());
            createdEvent.put("date", dateText);
            createdEvent.put("time", timeText);
            createdEvent.put("about", eventDesc.getEditText().getText().toString());
            createdEvent.put("category", category);
            createdEvent.put("venue", venue.getText().toString());
            createdEvent.put("latlong", latLong);
            try {
                createdEvent.put("id", createTransactionID());
            } catch (Exception e) {
                e.printStackTrace();
            }

            db.collection("events").add(createdEvent).addOnCompleteListener(documentReference -> {
                documentReference.addOnSuccessListener(unused -> {
                            Log.d("SUCCESS ADDING EVENT", "added successfully");
                            Toast.makeText(getContext(), "Success", Toast.LENGTH_LONG).show();
                            FragmentManager fragmentManager = getParentFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayout, new HomeFragment());
                            fragmentTransaction.commit();
                        })

                        .addOnFailureListener(e -> {
                            Log.d("ERROR ADDING EVENT", "error: " + e.getMessage());
                        });

                Log.d("DEBUG",
                        eventTitle.getEditText().getText().toString() + venue.getText()
                                + dateText
                                + timeText
                                + category);
            });
        });

        return layout;
    }

    public String createTransactionID() throws Exception {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    private void createDropdown(Spinner spinner) {
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<>();
        categories.add("Item 1");
        categories.add("Item 2");
        categories.add("Item 3");
        categories.add("Item 4");
        categories.add("Item 5");
        categories.add("Item 6");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == getActivity().RESULT_OK) {
            Place place = Autocomplete.getPlaceFromIntent(data);
            venue.setText(place.getAddress());
            locationName = place.getName();
            latLong = String.valueOf(place.getLatLng());

            Log.d("GOOGLE API", "locationname: " + locationName + " latlong:  " + latLong);
        } else if (requestCode == AutocompleteActivity.RESULT_ERROR) {
            Status status = Autocomplete.getStatusFromIntent(data);
            Log.d("GOOGLE API ERROR", "error: " + status);
        } else if (requestCode == 101 && resultCode == getActivity().RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData();
                InputStream inputStream = null;
                try {
                    inputStream = getActivity().getApplicationContext().getContentResolver().openInputStream(imageUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                bannerImage.setImageURI(data.getData());
                bannerImage.setImageBitmap(bitmap);
                bannerText.setVisibility(View.GONE);
                encodeImage = encodeImage(bitmap);
                Log.d("IMAGE", "onActivityResult: " + encodeImage);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        category = item;
    }

    public void onNothingSelected(AdapterView<?> item) {
        // TODO Auto-generated method stub
        AdapterView.OnItemClickListener onItemClickListener = item.getOnItemClickListener();

        Log.d("TAG", "onNothingSelected: " + onItemClickListener);
    }


    private String encodeImage(Bitmap bitmap) {
        int previewWidth = 100;
        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        previewBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }


}