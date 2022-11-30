package uk.ac.tees.b1642218.realgist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    Button btnSignup, btnForgotPassword, login;
    TextInputLayout txtEmail, txtPwd;

    FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            reload();

        }
    }

    private void reload() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        btnSignup = findViewById(R.id.btnSignup);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
        login = findViewById(R.id.btnLogin);

        auth = FirebaseAuth.getInstance();

        txtEmail = findViewById(R.id.txtEmail);
        txtPwd = findViewById(R.id.txtPwd);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Sign_up.class);
                startActivity(intent);

            }
        });

        btnForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Reset_pwd.class);
                startActivity(intent);
            }
        });
    }

    private Boolean validateEmail() {

        String val = txtEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            txtEmail.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(emailPattern)) {
            txtEmail.setError("invalid email");
            return false;
        } else {
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

        if (val.isEmpty()) {
            txtPwd.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordValue)) {
            txtPwd.setError("Does not meet complexity");
            return false;
        } else {
            txtPwd.setError(null);
            return true;
        }
    }


    public void btnLogin(View view) {

        if (!validateEmail() || !validatePassword()) {
            return;
        } else {

            String email = txtEmail.getEditText().getText().toString().trim();
            String password = txtPwd.getEditText().getText().toString().trim();

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        FirebaseUser user = auth.getCurrentUser();
                        updateUI(user);
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(Login.this, "Authentication failed", Toast.LENGTH_LONG).show();
                        updateUI(null);
                    }

                }

            });

        }


    }

    private void updateUI(FirebaseUser user) {
    }
}
    