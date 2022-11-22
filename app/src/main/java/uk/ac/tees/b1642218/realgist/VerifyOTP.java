package uk.ac.tees.b1642218.realgist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    private EditText OTPCode1, OTPCode2, OTPCode3, OTPCode4, OTPCode5, OTPCode6;
    private String verificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        TextView txtMobileNo = findViewById(R.id.txtMobileNo);
        txtMobileNo.setText(getIntent().getStringExtra("mobile"));

        OTPCode1 = findViewById(R.id.OTPCode1);
        OTPCode2 = findViewById(R.id.OTPCode2);
        OTPCode3 = findViewById(R.id.OTPCode3);
        OTPCode4 = findViewById(R.id.OTPCode4);
        OTPCode5 = findViewById(R.id.OTPCode5);
        OTPCode6 = findViewById(R.id.OTPCode6);

        setUpOTPInputs();

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button btnVerify = findViewById(R.id.btnVerify);

        verificationId = getIntent().getStringExtra("VerificationId");

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(OTPCode1.getText().toString().trim().isEmpty()
                        || OTPCode2.getText().toString().trim().isEmpty()
                        || OTPCode3.getText().toString().trim().isEmpty()
                        || OTPCode4.getText().toString().trim().isEmpty()
                        || OTPCode5.getText().toString().trim().isEmpty()
                        || OTPCode6.getText().toString().trim().isEmpty()){
                    Toast.makeText(VerifyOTP.this, "Please enter valid code", Toast.LENGTH_LONG).show();
                    return;
                }
                String code =
                        OTPCode1.getText().toString() +
                                OTPCode2.getText().toString() +
                                OTPCode3.getText().toString() +
                                OTPCode4.getText().toString() +
                                OTPCode6.getText().toString() +
                                OTPCode6.getText().toString();

                if (verificationId != null){
                    progressBar.setVisibility(View.VISIBLE);
                    btnVerify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationId,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    btnVerify.setVisibility(View.INVISIBLE);
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(), Splash_screen.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }else {Toast.makeText(VerifyOTP.this, "The Verification code is invalid", Toast.LENGTH_LONG).show();


                                    }

                                }
                            });
                }
            }
        });
        findViewById(R.id.txtresendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "countryCode" + getIntent().getStringExtra("mobile"), // country code + phone number
                        60,// user should be able to get an new code in 60 seconds
                        TimeUnit.SECONDS,
                        VerifyOTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(VerifyOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String newVerificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                               verificationId = newVerificationId;
                               Toast.makeText(VerifyOTP.this, "OTP Sent", Toast.LENGTH_LONG).show();
                            }
                        }
                );

            }
        });
    }

    private void setUpOTPInputs(){
        OTPCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()){
                  OTPCode2.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        OTPCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()){
                    OTPCode3.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        OTPCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()){
                    OTPCode4.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        OTPCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()){
                    OTPCode4.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        OTPCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()){
                    OTPCode6.requestFocus();

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



    }
}