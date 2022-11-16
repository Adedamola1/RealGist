package uk.ac.tees.b1642218.realgist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class SignUp2 extends AppCompatActivity {

    ImageView backbutton2;

    private DatePickerDialog datePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup2);

        //Hooks
        backbutton2 = (ImageView) findViewById(R.id.signup2_back_btn);
        backbutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp2.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        };


    public void btnDone(View view){

        Intent intent = new Intent(SignUp2.this, SignUp2.class);
    }
}