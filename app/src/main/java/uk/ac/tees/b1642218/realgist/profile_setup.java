package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;

public class profile_setup extends AppCompatActivity {

    //Declare Variables

    TextInputLayout txtFirstName,txtLastName, txtUsername, phoneNo, txtPassword,
    txtConfirmPassword;
    Button btnRegisterUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile_setup);
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
        String noWhiteSpace = "(?=\\s+$)";

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
                "(?=.*[@#$%^&+=)" + // at least 1 special charater
                "(?=\\S+$)" +  //No white spaces
                ".{8,}" + // atleast 8 characters
                "$";

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
        if (!validateFirstName() || !validateLastName() || !validateUsername()
        || !validatePhoneNo() || validatePassword() );
        return;

    }
    String firstName = txtFirstName.getEditText().getText().toString();
    String lastName = txtLastName.getEditText().getText().toString();
    String username = txtUsername.getEditText().getText().toString();
    String phoneNumber = phoneNo.getEditText().getText().toString();
    String password = txtPassword.getEditText().getText().toString();
    UserHelperClass helperClass = new UserHelperClass(firstName, lastName,
            username,phoneNumber,password);
}

