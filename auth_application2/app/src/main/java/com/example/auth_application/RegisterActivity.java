package com.example.auth_application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity {

    private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient http = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registeractivtity);

        // Insets Adjustment (for edge-to-edge UI)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnCreateAccount = findViewById(R.id.createaccountbtn);
        btnCreateAccount.setOnClickListener(v -> {
            // Get input values
            EditText fname = findViewById(R.id.fname);
            EditText lname = findViewById(R.id.lname);
            EditText email = findViewById(R.id.email);
            EditText mobile = findViewById(R.id.mobile);
            EditText password = findViewById(R.id.password);

            String firstname = fname.getText().toString().trim();
            String lastname = lname.getText().toString().trim();
            String emailid = email.getText().toString().trim();
            String mobilenum = mobile.getText().toString().trim();
            String passwrod = password.getText().toString().trim();

            // Validate inputs
            if (firstname.isEmpty() || emailid.isEmpty() || passwrod.isEmpty()) {
                Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Create JSON body
            JSONObject obj = new JSONObject();
            try {
                obj.put("firstname", firstname);
                obj.put("lastname", lastname);
                obj.put("email", emailid);
                obj.put("mobile", mobilenum);
                obj.put("password", passwrod);
            } catch (JSONException e) {
                Toast.makeText(this, "JSON Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                return;
            }

            // Build request
            RequestBody requestBody = RequestBody.create(obj.toString(), JSON);
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:3000/course/register")
                    .post(requestBody)
                    .build();

            // Send request in background thread
            new Thread(() -> {
                try (Response response = http.newCall(request).execute()) {
                    if (response.isSuccessful()) {
                        runOnUiThread(() ->
                                Toast.makeText(this, "Registration Success", Toast.LENGTH_LONG).show());
                        Intent intent  = new Intent(RegisterActivity.this,LmsActivity.class);
                        startActivity(intent);
                        finish();


                    } else {
                        runOnUiThread(() ->
                                Toast.makeText(this, "Server Error: " + response.code(), Toast.LENGTH_LONG).show());
                    }
                } catch (IOException e) {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Network Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }).start();
        });
    }
}
