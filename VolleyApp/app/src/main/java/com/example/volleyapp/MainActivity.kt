package com.example.volleyapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

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
        
       var submitBtn = findViewById<Button>(R.id.serchBtn);
        submitBtn.setOnClickListener {
            val drivername = findViewById<EditText>(R.id.DriverName).text.toString()
            val mobilenumber = findViewById<EditText>(R.id.mobnumber).text.toString()

            val jsonBody = JSONObject().apply {
                put("name", drivername)
                put("mob", mobilenumber)
            }

            val que = Volley.newRequestQueue(this)
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.POST,
                "http://10.164.218.159:3000/driver",
                jsonBody,
                { response ->
                    Toast.makeText(this, "Registration Success", Toast.LENGTH_LONG).show()
                    Log.d("Response", response.toString())
                },
                { error ->
                    Toast.makeText(this, "Registration Error: ${error.message}", Toast.LENGTH_LONG).show()
                    Log.e("VolleyError", error.toString())
                }
            )

            que.add(jsonObjectRequest)
        }

        findViewById<Button>(R.id.findPagebtn).setOnClickListener {
            var intent = Intent(this, SearchDriverActivity::class.java);
            startActivity(intent);
        }


    }
}