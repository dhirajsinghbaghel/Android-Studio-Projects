package com.example.nootbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.DocumentTransform;

import java.util.HashMap;
import java.util.Map;

public class Createnotes extends AppCompatActivity {
        private FirebaseAuth auth;
       private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createnotes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

      auth =  FirebaseAuth.getInstance();
     FirebaseUser session = auth.getCurrentUser();
     if (session==null){
         Intent intent  = new Intent(Createnotes.this,Loginactivity.class);
         startActivity(intent);
         return;
     }
     String uid = session.getUid();

     db = FirebaseFirestore.getInstance();
        Button savebtn = findViewById(R.id.create_btn);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Map<String,Object > data = new HashMap<>();
                EditText title_input  = findViewById(R.id.title_input);
                String title =  title_input.getText().toString();
                EditText desc_input  = findViewById(R.id.notes_desc_input);
                String desc =  desc_input.getText().toString();
                data.put("uid",uid);
                data.put("title",title);
                data.put("discription",desc);
                data.put("createdAt", FieldValue.serverTimestamp());

                savebtn.setEnabled(false);
                savebtn.setText(R.string.processing);

                db.collection("notes").add(data).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                      Intent intent =   new Intent(Createnotes.this, Noteactivty.class);
                      startActivity(intent);
                      finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {

                    @Override
                    public void onFailure(@NonNull Exception e) {
                        savebtn.setEnabled(true);
                        savebtn.setText(R.string.create);
                        Toast.makeText(Createnotes.this, "Notes Creation Failed", Toast.LENGTH_LONG).show();
                    }
                });



            }
        });

    }
}