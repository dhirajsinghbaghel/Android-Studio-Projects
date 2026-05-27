package com.example.nootbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loginactivity extends AppCompatActivity {

    private FirebaseAuth auth;
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

        TextView switch_btn = findViewById(R.id.switch_to_sign_up);
        switch_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                            Intent intent = new Intent(Loginactivity.this, Signup_activity.class);
                            startActivity(intent);
                            finish();
                }
            });

        auth = FirebaseAuth.getInstance();
       Button signinbtn = findViewById(R.id.sign_in_btn);
        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText email_input = findViewById(R.id.login_email_input);
                String email = email_input.getText().toString();
                EditText password_input = findViewById(R.id.login_password_input);
                String password =  password_input.getText().toString();
                    signinbtn.setEnabled(false);
                    signinbtn.setText(R.string.processing);
                ProgressBar loginloader = findViewById(R.id.login_loader);
                loginloader.setVisibility(View.VISIBLE);
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(Loginactivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loginloader.setVisibility(View.GONE);
                        signinbtn.setEnabled(true);
                        signinbtn.setText(R.string.sign_in);
                        if (task.isSuccessful()){
                          Intent notintent = new Intent(Loginactivity.this, Noteactivty.class);
                          startActivity(notintent);
                          finish();
                        } else{
                            Toast.makeText(Loginactivity.this, "Invalid Email Id or Password .", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        FirebaseUser session = auth.getCurrentUser();
        if (session!=null){
            Intent intent = new Intent(Loginactivity.this,Noteactivty.class);
            startActivity(intent);
            finish();
        }

    }
}