package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sign_up extends AppCompatActivity {


    Button submit;
    ImageView backbutton;
    TextView titleText;
    //Declare Variables for validation
    TextInputLayout txtFirstName,txtLastName, txtUsername, txtEmail, txtPwd,
            txtconfirmPwd;
    //variables for date of birth




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //Hooks

        submit = findViewById(R.id.btnRegisterUser);
        titleText = findViewById(R.id.txtSignup_welcome);
        backbutton = findViewById(R.id.signup_back_btn);

        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastname);
        txtUsername = findViewById(R.id.txtUsername);
        txtEmail = findViewById(R.id.txtEmail);
        txtPwd = findViewById(R.id.txtPwd);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        boolean hasWhiteSpace = Pattern.matches("\\s+", val);

        if (val.isEmpty()){
            txtUsername.setError("Field cannot be empty");
            return false;
        }
        else if (val.length() >= 10){
            txtUsername.setError("Username too long");
            return false;
        }
        else if (val.contains(" ")){
            txtUsername.setError("should not have white space");
            return false;
        }
        else{
            txtUsername.setError(null);
            return true;
        }
    }

    private Boolean validateEmail() {

        String val = txtEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            txtEmail.setError("Field cannot be empty");
            return false;
        }else if (!val.matches(emailPattern)){
            txtEmail.setError("invalid email");
            return false;
        }
        else{
            txtEmail.setError(null);

            return true;
        }
    }

    private Boolean validatePassword() {

        String val = txtPwd.getEditText().getText().toString();
        String passwordValue = "^" +
                "(?=.*[a-zA-Z])" + //any letter
                "(?=.+?[0-9])" + //at least 1 digit
                "(?=.*[@#$%^&+=])" + // at least 1 special charater
                "(?=\\S+$)" +  //No white spaces
                ".{8,}" +
                "$"; // atleast 8 characters;

        if (val.isEmpty()){
            txtPwd.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordValue)){
            txtPwd.setError("Does not meet complexity");
            return false;
        }
        else{
            txtPwd.setError(null);
            return true;
        }
    }

    private Boolean validateComfirmPassword() {

        String val = txtconfirmPwd.getEditText().getText().toString();

        if
        (val.isEmpty()){
            txtconfirmPwd.setError("Field cannot be empty");
            return false;
        }
        else{
            txtconfirmPwd.setError(null);
            return true;
        }
    }


    public void btnRegisterUser(View view){

        Intent intent = new Intent(Sign_up.this, SignUp2.class);

        //Add Transition
        Pair[] pairs = new Pair[3];

        pairs[0] = new Pair<View,String>(submit, "transition_submit_btn");
        pairs[1] = new Pair<View,String>(titleText, "transition_title_txt");
        pairs[2] = new Pair<View,String>(backbutton, "transition_signup_back_btn");


        if (!validateFirstName() || !validateLastName() || !validateUsername()
        || !validateEmail() || validatePassword()){
            return;
        }

        else {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Sign_up.this, pairs);
            startActivity(intent, options.toBundle());

        }



    }
}

