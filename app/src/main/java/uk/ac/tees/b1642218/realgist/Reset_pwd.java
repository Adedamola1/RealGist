package uk.ac.tees.b1642218.realgist;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Reset_pwd extends AppCompatActivity {

    TextInputLayout txtResetEmail;
    Button btnResetPassword, backToLogin;
    private String email;

    //validate email
    private Boolean validateEmail() {
        String val = txtResetEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            txtResetEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            txtResetEmail.setError("invalid email");
            return false;
        } else {
            txtResetEmail.setError(null);

            return true;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pwd);


        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reset_pwd.this, VerifyOTP.class);
                startActivity(intent);
            }
        });

        backToLogin = findViewById(R.id.btnback_to_login);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        txtResetEmail = findViewById(R.id.txtEmail);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtResetEmail.getEditText().getText().toString();


                if (email.isEmpty()) {
                    Toast.makeText(Reset_pwd.this, "Please enter email", Toast.LENGTH_SHORT).show();
                } else {
                    btnResetPassword();
                }
            }
        });
    }

    private void btnResetPassword() {

        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Reset_pwd.this, "Check your email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Reset_pwd.this, Log.class));
                            finish();
                        } else {
                            Toast.makeText(Reset_pwd.this, "Error :" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}
