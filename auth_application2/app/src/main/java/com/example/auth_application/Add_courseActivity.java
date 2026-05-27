package com.example.auth_application;

import android.os.Bundle;
import android.util.Log;
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
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Add_courseActivity extends AppCompatActivity {
 private final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private  final OkHttpClient  http = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_addcourse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button creteBtn = findViewById(R.id.coursecreatebtn);
        creteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText cname = findViewById(R.id.coursename);
               String courseName = cname.getText().toString().trim();
                EditText cPrice = findViewById(R.id.courseprice);
                String coursePrice = cPrice.getText().toString().trim();
                EditText cDeu = findViewById(R.id.courseduration);
                String courseDeu = cDeu.getText().toString().trim();
                EditText cDesc = findViewById(R.id.coursedescription);
                String courseDesc = cDesc.getText().toString().trim();

//                Log.i("courseName",courseName);
               JSONObject obj =  new JSONObject();
                try {
                    obj.put("title",courseName);
                    obj.put("description",courseDesc);
                    obj.put("price",coursePrice);
                    obj.put("duration",courseDeu);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                RequestBody data =  RequestBody.create(obj.toString(),JSON);
                Request req =new Request.Builder()
                        .url("http://10.0.2.2:3000/course/create")
                        .post(data)
                        .build();
                new Thread(() -> {
                    try (Response res = http.newCall(req).execute()) {
                        if (res.isSuccessful()) {
                            runOnUiThread(() ->
                                    Toast.makeText(Add_courseActivity.this, "success", Toast.LENGTH_LONG).show()
                            );
                        } else {
                            runOnUiThread(() ->
                                    Toast.makeText(Add_courseActivity.this, "API Error: " + res.code(), Toast.LENGTH_LONG).show()
                            );
                        }
                    } catch (IOException e) {
                        runOnUiThread(() ->
                                Toast.makeText(Add_courseActivity.this, "Failed: " + e.getMessage(), Toast.LENGTH_LONG).show()
                        );
                        Log.e("API_ERROR", Objects.requireNonNull(e.getMessage()));
                    }
                }).start();

            }
        });


    }
}