package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class Sign_up extends AppCompatActivity {

    //Declare Variables for validation
    TextInputLayout txtFirstName,txtLastName, txtUsername, phoneNo, txtPassword,
    txtConfirmPassword;
    Button submit;
    TextView titleText;

    //variables for date of birth
    private DatePickerDialog datePickerDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //Hooks
        submit = findViewById(R.id.btnRegisterUser);
        titleText = findViewById(R.id.txtSignup_welcome);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastname);
        txtUsername = findViewById(R.id.txtUsername);
        phoneNo = findViewById(R.id.phoneNo);
        txtPassword = findViewById(R.id.txtPassword);

//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                if(!validateFirstName()){
//                    return;
//                }
//                else {
//                    Intent intent = new Intent(Sign_up.this,SignUp2.class);
//                    startActivity(intent);
//                }
//
//            }
//        });

    }

    private Boolean validateFirstName() {
        String val = txtFirstName.getEditText().getText().toString();
        if (val.isEmpty()){
            txtFirstName.setError("Field cannot be empty");
            return false;
        }
        else{
            txtFirstName.setError(null);
            txtFirstName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateLastName() {

        String val = txtLastName.getEditText().getText().toString();

        if (val.isEmpty()){
            txtLastName.setError("Field cannot be empty");

            return false;
        }
        else{
            txtLastName.setError(null);
            txtLastName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {

        String val = txtUsername.getEditText().getText().toString();
        String noWhiteSpace = "(?=\\S+$)";

        if (val.length() >= 10){
            txtFirstName.setError("Username too long");
            return false;
        }
        else if (!val.matches(noWhiteSpace)){
            txtUsername.setError("should not have white space");
            return false;
        }
        else{
            txtUsername.setError(null);
            return true;
        }
    }

    private Boolean validatePhoneNo() {

        String val = phoneNo.getEditText().getText().toString();

        if (val.isEmpty()){
            phoneNo.setError("Field cannot be empty");
            return false;
        }
        else{
            phoneNo.setError(null);
            return true;
        }
    }

    private Boolean validatePassword() {

        String val = txtPassword.getEditText().getText().toString();
        String passwordValue = "^" +
                "(?=.*[a-zA-Z])" + //any letter
                "(?=.+?[0-9])" + //at least 1 digit
                "(?=.*[@#$%^&+=])" + // at least 1 special charater
                "(?=\\S+$)" +  //No white spaces
                ".{8,}" +
                "$"; // atleast 8 characters;

        if (val.isEmpty()){
            txtPassword.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordValue)){
            txtPassword.setError("Does not meet complexity");
            return false;
        }
        else{
            txtPassword.setError(null);
            return true;
        }


    }

    private Boolean validateComfirmPassword() {

        String val = txtFirstName.getEditText().getText().toString();

        if (val.isEmpty()){
            txtFirstName.setError("Field cannot be empty");
            return false;
        }
        else{
            txtFirstName.setError(null);
            return true;
        }
    }


    public void btnRegisterUser(View view){

        Intent intent = new Intent(Sign_up.this, SignUp2.class);


        //Add Transition
        Pair[] pairs = new Pair[2];

        pairs[0] = new Pair<View,String>(submit, "transition_submit_btn");
        pairs[1] = new Pair<View,String>(titleText, "transition_title_txt");



        if (!validateFirstName() || !validateLastName() || !validateUsername()
        || !validatePhoneNo() || validatePassword()){
            return;
        }

        else {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Sign_up.this, pairs);
            startActivity(intent, options.toBundle());

        }




    }
}

