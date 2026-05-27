package com.example.studentapp

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn = findViewById<AppCompatButton>(R.id.s_formBtn);
        btn.setOnClickListener {
            val snameinput = findViewById<EditText>(R.id.studentname);
            val sname = snameinput.text.toString();

            val semailinput = findViewById<EditText>(R.id.studentEmail);
            val semail = semailinput.text.toString();

            val ssubjectinput = findViewById<EditText>(R.id.studentSubject);
            val ssubject = ssubjectinput.text.toString();

            val spasswordinput = findViewById<EditText>(R.id.studentPassword);
            val spassword = spasswordinput.text.toString();

            val sroll = findViewById<EditText>(R.id.studentrollnum);
            val srollnum = sroll.text.toString();

            val intent = Intent(this, MainActivity2::class.java);
            intent.putExtra("name",sname);
            intent.putExtra("email",semail);
            intent.putExtra("subject",ssubject);
            intent.putExtra("rollnum",srollnum);
            startActivity(intent);


        }

    }
}