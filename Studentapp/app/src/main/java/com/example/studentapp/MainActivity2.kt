package com.example.studentapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = intent.getStringExtra("name");
        val email = intent.getStringExtra("email");
        val subject = intent.getStringExtra("subject");
        val rollnum = intent.getStringExtra("rollnum");


        val studentname = findViewById<TextView>(R.id.studentname);
        studentname.text = name;

        val studentemail = findViewById<TextView>(R.id.emailid);
        studentemail.text = "Email :"+email;

        val studentsubject = findViewById<TextView>(R.id.studentSubject);
        studentsubject.text="Subject Name :"+subject;

        val studentrollnum = findViewById<TextView>(R.id.rollnum);
        studentrollnum.text =  "Roll No :"+rollnum;


        val backbtn = findViewById<ImageView>(R.id.backbtn);
        backbtn.setOnClickListener {
            finish();
        }




    }
}