package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class Onboard extends AppCompatActivity {
//Declare variables
    TextInputLayout phoneNumber;
    Button btnVerify;
    ImageView onboardbackbtn;
    CountryCodePicker countryCode;

//validate email
    private Boolean validatePhoneNumber (){

        return null;
    }
    //register user in firebase


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_onboard);

        btnVerify = findViewById(R.id.btnVerify);
        onboardbackbtn = findViewById(R.id.onboard_back_btn);
        countryCode = findViewById(R.id.countryCode);

        onboardbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validate email
                if (!validatePhoneNumber()){
                    return;}


                //Get all neccessary values
                String _firstName = getIntent().getStringExtra("firstName");
                String _lastName = getIntent().getStringExtra("lastName");
                String _username = getIntent().getStringExtra("username");
                String _email = getIntent().getStringExtra("email");
                String _postCode = getIntent().getStringExtra("postCode");
                String _gender = getIntent().getStringExtra("gender");
                String _password = getIntent().getStringExtra("password");

                String _getEnteredPhone = phoneNumber.getEditText().getText().toString().trim(); //Get Phone number
                String _phoneNo = "+"+countryCode.getFullNumber()+_getEnteredPhone;


                Intent intent = new Intent(getApplicationContext(), VerifyOTP.class);
                intent.putExtra("firstName", _firstName);
                intent.putExtra("lastName", _lastName);
                intent.putExtra("userName", _username);
                intent.putExtra("email", _email);
                intent.putExtra("postCode", _postCode);
                intent.putExtra("gender", _gender);
                intent.putExtra("password", _password);
                intent.putExtra("phoneNo", _phoneNo);

                startActivity(new Intent(Onboard.this, Login.class));
                Toast.makeText(Onboard.this, R.string.OTP_Sent, Toast.LENGTH_LONG).show();

            }
        });

    }


}