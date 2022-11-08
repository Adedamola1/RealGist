package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class passwordSetup extends AppCompatActivity {

    TextInputLayout password;

    Button btnAcceptPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_password_setup);

        btnAcceptPassword = findViewById(R.id.btnAcceptPassword);

        btnAcceptPassword.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                Intent intent = new Intent(passwordSetup.this, profile_setup.class);
                startActivity(intent);
            }

        });


        }
    public void onBtnSignUp (View view){

    }
    }


