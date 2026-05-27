package com.example.kotlintest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
        val btn = findViewById<Button>(R.id.mybutton);
        btn.setOnClickListener {
            val taxttag = findViewById<TextView>(R.id.textView);

            Toast.makeText(this,"clickbtn", Toast.LENGTH_LONG).show();
            val word = taxttag.text.toString();
            if (word=="Welcome First Kotlin Class"){
                taxttag.setText("Hii Student");
                taxttag.setTextColor(Color.RED);
            }else{
                taxttag.setText("Welcome First Kotlin Class");
                taxttag.setTextColor(Color.GREEN);
            }
        }
    }
}