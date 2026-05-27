package com.example.mistribaba

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
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

class MainActivity : AppCompatActivity() {

    // AlertDialog helper function
    private fun showAlertDialog(context: Context, title: String, message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    // Loading dialog
    private var loadingDialog: AlertDialog? = null

    private fun showLoading(context: Context, message: String = "Please wait...") {
        if (loadingDialog != null && loadingDialog!!.isShowing) return

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_loading, null)
        val msgText = view.findViewById<TextView>(R.id.loading_message)
        msgText.text = message

        val builder = AlertDialog.Builder(context)
        builder.setView(view)
        builder.setCancelable(false)

        loadingDialog = builder.create()
        loadingDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        loadingDialog?.show()
    }

    private fun dismissLoading() {
        loadingDialog?.dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Redirect to Sign Up page
        val reDirectSignUpPage = findViewById<TextView>(R.id.reDirect_SignUpPage)
        reDirectSignUpPage.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        // Login button click
        val loginButton = findViewById<TextView>(R.id.login_button)
        loginButton.setOnClickListener {
            val loginPhone = findViewById<EditText>(R.id.login_phone)
            val loginPassword = findViewById<EditText>(R.id.login_password)

            val mobile = loginPhone.text.toString().trim()
            val password = loginPassword.text.toString().trim()

            if (mobile.isEmpty() || password.isEmpty()) {
                showAlertDialog(this, "Login Failed", "Please enter phone and password")
                return@setOnClickListener
            }

            // Show Loading Dialog
            showLoading(this, "Processing...")

            val queue = Volley.newRequestQueue(this)
            val data = JSONObject().apply {
                put("mobilenumber", mobile)
                put("userpassword", password)
            }

            val request = JsonObjectRequest(
                Request.Method.POST,
                userinfo.backendurl + "/api/user-login",
                data,
                { response ->
                    dismissLoading()
                    userinfo.usermobile = mobile
                    userinfo.isLogin = true
                    startActivity(Intent(this, DashbordActivity::class.java))
                },
                { error ->
                    dismissLoading()
                    showAlertDialog(this, "Login Failed", "Login failed: ${error.message}")
                }
            )

            queue.add(request)
        }
    }
}
