package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class Onboard extends AppCompatActivity {
//Declare variables
    TextInputLayout txtEmail;
    Button btnVerify;
    ImageView onboardbackbtn;
//validate email
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboard);

        btnVerify = findViewById(R.id.btnVerify);
        onboardbackbtn = findViewById(R.id.onboard_back_btn);

        onboardbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Onboard.this, Sign_up.class);
                startActivity(intent);

            }
        });
    }

}