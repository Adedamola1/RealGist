package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

public class Reset_pwd extends AppCompatActivity {

    TextInputLayout txtResetEmail;
    Button btnResetPassword, backToLogin;

    //validate email
    private Boolean validateEmail(){
        String val = txtResetEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()){
            txtResetEmail.setError("Field cannot be empty");
            return false;
        }else if (!val.matches(emailPattern)){
            txtResetEmail.setError("invalid email");
            return false;
        }
        else{
            txtResetEmail.setError(null);

            return true;
    }


    } @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_reset_pwd);



            btnResetPassword = findViewById(R.id.btnResetPassword);
            btnResetPassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Reset_pwd.this, Login.class);
                    startActivity(intent);
                }
            });

        backToLogin = findViewById(R.id.btnback_to_login);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}