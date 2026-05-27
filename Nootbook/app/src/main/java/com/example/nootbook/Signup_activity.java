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
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup_activity extends AppCompatActivity {

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();

        EditText fullname_input = findViewById(R.id.register_fullname);
        EditText email_input = findViewById(R.id.register_email);
        EditText password_input = findViewById(R.id.register_password);
        Button singupBtn = findViewById(R.id.signup_button);


        singupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname = fullname_input.getText().toString();
                String email = email_input.getText().toString();
                String password = password_input.getText().toString();
                    if (fullname.isEmpty()){
                        fullname_input.setError("Full Name Is Required");
                        return;
                    }
                    if (email.isEmpty()){
                        email_input.setError("Email Is Required");
                        return;
                    }
                    if (password.isEmpty()){
                        password_input.setError("Password Is Required");
                        return;
                    }
                singupBtn.setEnabled(false);
                   singupBtn.setText("Processing..");
                ProgressBar loader = findViewById(R.id.loader);
                loader.setVisibility(View.VISIBLE);
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(Signup_activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        singupBtn.setEnabled(true);
                        singupBtn.setText(R.string.ragisternow);
                        loader.setVisibility(View.GONE);
                      Exception  exce =task.getException();
                       if (exce.getMessage()!=null){
                           String msg = exce.getMessage();
                           Toast.makeText(Signup_activity.this, msg, Toast.LENGTH_LONG).show();
                           return;
                       }

                        Intent switchtoNoteintent =  new Intent(Signup_activity.this,Noteactivty.class);
                       startActivity(switchtoNoteintent);
                       finish();
                    }
                });
            }
        });




//        switch to  loginactivity
        TextView switch_btn = findViewById(R.id.switch_to_sign_in);
        switch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup_activity.this, Loginactivity.class);
                startActivity(intent);
                finish();
            }
        });

        FirebaseUser session = auth.getCurrentUser();
        if (session!=null){
            Intent intent = new Intent(Signup_activity.this,Noteactivty.class);
            startActivity(intent);
            finish();
        }

    }
}