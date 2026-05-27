package com.example.newfoodapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Loginactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loginactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextView btn = findViewById(R.id.signup_page);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Loginactivity.this, Rajistration.class);
                startActivity(intent);
            }
        });

//        login coding start
        Button loginbtn = findViewById(R.id.login_button);
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email_input = findViewById(R.id.login_emailinput);
                EditText pass_input = findViewById(R.id.login_passinput);
                String email = email_input.getText().toString();
                String pass = pass_input.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(Loginactivity.this, "Email Fild Is Required", Toast.LENGTH_LONG).show();
                } else {
                    if (email.equals("dhiraj@gmail.com")) {
                        if (pass.isEmpty()) {
                            Toast.makeText(Loginactivity.this, "Password is Required", Toast.LENGTH_LONG).show();
                        } else {
                            if (pass.equals("1234")) {
                                Intent prointent = new Intent(Loginactivity.this,Profile.class);
                                startActivity(prointent);
                            } else {
                                Toast.makeText(Loginactivity.this, "Password is Wrong", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        Toast.makeText(Loginactivity.this, "User Not Found", Toast.LENGTH_LONG).show();
                    }

                }
            };
        });




//        login codin end

    }
}