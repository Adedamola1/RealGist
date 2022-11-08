package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;


import com.google.android.material.textfield.TextInputLayout;

public class profile_setup extends AppCompatActivity {

    //Declare Variables

    TextInputLayout firstName,lastName, userName, phoneNo;
    Button btnRegisterUser;




    Button  btnVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_profile_setup);
    }




    public void btnRegisterUser(View view){


    }
}

