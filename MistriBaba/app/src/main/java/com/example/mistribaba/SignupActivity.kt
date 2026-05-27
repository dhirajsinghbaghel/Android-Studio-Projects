package com.example.mistribaba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.apply

class SignupActivity : AppCompatActivity() {

    private var alertDialog: AlertDialog? = null

    private fun showAlertDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
    private fun showLoading(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setCancelable(false)
        alertDialog = builder.create()
        alertDialog?.show()
    }

    private fun dismissLoading() {
        alertDialog?.dismiss()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val reDirect_SignInPage = findViewById<TextView>(R.id.reDirect_SignInPage);
        reDirect_SignInPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }



        var registerBtn = findViewById<TextView>(R.id.registerBtn);
        registerBtn.setOnClickListener {
            var phone = findViewById<TextView>(R.id.phone);
            var Name = findViewById<TextView>(R.id.Name);
            var password = findViewById<TextView>(R.id.password);
            var cPassword = findViewById<TextView>(R.id.cPassword);
            if(phone.text.isEmpty() || Name.text.isEmpty() || password.text.isEmpty() || cPassword.text.isEmpty()){
                showAlertDialog(this, "Registration Failed", "Please Enter All Fields")
                return@setOnClickListener
            }
            if(password.text.toString() != cPassword.text.toString()){
                showAlertDialog(this, "Password Mismatch", "Password Mismatch")
                return@setOnClickListener
            }
            var data = JSONObject().apply {
                put("mobilenumber", phone.text.toString())
                put("fullname", Name.text.toString())
                put("userpassword", password.text.toString())
                put("usertype","2")
            };
            var req = Volley.newRequestQueue(this);
            showLoading(this, "Please Wait", "Loading...")
            var jar = JsonObjectRequest(Request.Method.POST,
                userinfo.backendurl+"/api/register",
                data,
                {
                    response->
                    dismissLoading();
                    showAlertDialog(this, "Registration Successful", "Registration Successful")
                },
                {
                    error->;
                    dismissLoading();
                    showAlertDialog(this, "Registration Failed", "Registration failed: ${error.message}")

                }

            );
            req.add(jar);


        }

    }
}