package com.example.nootbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class Noteactivty extends AppCompatActivity {
    private FirebaseFirestore db;
    private  FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_noteactivty);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        db= FirebaseFirestore.getInstance();
        FirebaseUser session = auth.getCurrentUser();
        if (session==null){
           Intent checkuser = new Intent(Noteactivty.this,Loginactivity.class);
           startActivity(checkuser);
           return;
        }
        String loginemail =  session.getEmail();
        TextView useremailtxt = findViewById(R.id.user_email_id);
        useremailtxt.setText(loginemail);

        Button logout_btn = findViewById(R.id.logout_btn);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent loginact = new Intent(Noteactivty.this,Loginactivity.class);
                startActivity(loginact);
                finish();
            }
        });

        FloatingActionButton switch_tocreatenotes = findViewById(R.id.switch_to_createnotes);
        switch_tocreatenotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Noteactivty.this,Createnotes.class);
                startActivity(intent);
            }
        });

        db.collection("notes").whereEqualTo("uid",session.getUid()).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    LinearLayout design_box = findViewById(R.id.design_cont);
                    for (QueryDocumentSnapshot document : task.getResult()){
                        Map<String,Object> data = document.getData();
                        String title = (String) data.get("title");
                        String description = (String) data.get("discription");
                        assert title != null;
                        TextView cardTitel = new TextView(Noteactivty.this);
                        cardTitel.setText(title);
                        cardTitel.setTextSize(30);
                        design_box.addView(cardTitel);

                    }
                }
            }
        });


    }

}