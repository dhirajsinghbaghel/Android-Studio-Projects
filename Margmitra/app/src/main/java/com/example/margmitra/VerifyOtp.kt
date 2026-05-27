package com.example.margmitra

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VerifyOtp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_verify_otp)

        // Edge handling
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val verifyOtp = findViewById<Button>(R.id.verifyotp)
        verifyOtp.setOnClickListener {
            val otp = buildString {
                append(findViewById<EditText>(R.id.et1).text)
                append(findViewById<EditText>(R.id.et2).text)
                append(findViewById<EditText>(R.id.et3).text)
                append(findViewById<EditText>(R.id.et4).text)
            }

            if (otp.length == 4) {
                Toast.makeText(this, "OTP Verified: $otp", Toast.LENGTH_SHORT).show()
                // Navigate to HomeActivity safely
                startActivity(Intent(this, MainActivity::class.java))
                finishAffinity() // clears previous stack (no back to login)
            } else {
                Toast.makeText(this, "Please enter complete OTP", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
