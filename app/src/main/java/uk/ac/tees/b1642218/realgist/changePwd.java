package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class changePwd extends AppCompatActivity {

    TextInputLayout password, confirmpassword;
    Button btnAcceptPassword;
    ImageView resetbackbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        btnAcceptPassword = findViewById(R.id.btnResetPwd_Submit);
        password = findViewById(R.id.txtchangePwd);

        resetbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private Boolean validatePassword() {

        String val = password.getEditText().getText().toString();
        String passwordValue = "^" +
                "(?=.*[a-zA-Z])" + //any letter
                "(?=.+?[0-9])" + //at least 1 digit
                "(?=.*[@#$%^&+=])" + // at least 1 special charater
                "(?=\\S+$)" +  //No white spaces
                ".{8,}" +
                "$"; // atleast 8 characters;

        if (val.isEmpty()){
            password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordValue)){
            password.setError("Does not meet complexity");
            return false;
        }
        else{
            password.setError(null);
            return true;
        }
    }

    public void btnAcceptPassword(View view){

        Intent intent = new Intent(changePwd.this, Login.class);

        if (validatePassword()){
            return;
        }

        btnAcceptPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(changePwd.this, Sign_up.class);
                startActivity(intent);

            }
        });
        }
    }


