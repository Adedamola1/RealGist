//package uk.ac.tees.b1642218.realgist;
//
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Bundle;
//import android.util.Base64;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;
//import androidx.fragment.app.Fragment;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.io.ByteArrayOutputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//import java.util.HashMap;
//
//
//public class ProfileFragment extends Fragment implements AdapterView.OnItemSelectedListener{
//
//    ImageView profile_selfie;
//    TextView addImage;
//    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            result -> {
//                if (result.getResultCode() == getActivity().RESULT_OK) {
//                    if (result.getData() != null) {
//                        Uri imageUri = result.getData().getData();
//
//                        InputStream inputStream = null;
//                        try {
//                            inputStream = getContext().getContentResolver().openInputStream(imageUri);
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        }
//                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                        profile_selfie.setImageURI(result.getData().getData());
//                        profile_selfie.setImageBitmap(bitmap);
//                        addImage.setVisibility(View.GONE);
//                        encodeImage = encodeImage(bitmap);
//
//
//                    }
//                }
//            }
//    );
//    Button edit;
//    FirebaseAuth auth;
//    FirebaseUser user;
//    private View layout;
//    String fullName;
//    View username;
//    View phoneNumber;
//    View email;
//    String encodeImage;
//    EditText address;
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false);
//        edit = layout.findViewById(R.id.btnProfileEdit);
//        addImage = layout.findViewById(R.id.add_event_banner);
//        address = layout.findViewById(R.id.address);
//        email = layout.findViewById(R.id.profileEmail);
//        phoneNumber = layout.findViewById(R.id.phoneNo);
//        username = layout.findViewById(R.id.txtUsername);
//
//        FrameLayout imageLayout = layout.findViewById(R.id.profileImageLayout);
//
//    }
//    private String encodeImage(Bitmap bitmap) {
//        int previewWidth = 150;
//        int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
//        Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap, previewWidth, previewHeight, false);
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        previewBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] bytes = byteArrayOutputStream.toByteArray();
//        return Base64.encodeToString(bytes, Base64.DEFAULT);
//
//        edit.setOnClickListener(v -> {
//            FirebaseFirestore db = FirebaseFirestore.getInstance();
//            HashMap<String, Object> user = new HashMap<>();
//
//            user.put("eventImage", encodeImage);
//            user.put("fullname", txtFirstName.getEditText().getText().toString().trim());
//            user.put("full Name", user.g);
//    }
//}