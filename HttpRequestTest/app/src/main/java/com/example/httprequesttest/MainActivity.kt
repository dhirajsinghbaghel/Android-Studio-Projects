package com.example.httprequesttest

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
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

        var http = OkHttpClient();
        var host = "https://jsonplaceholder.typicode.com";
        var type = "aplication/json; charset=utf-8".toMediaType();
        val postBtn = findViewById<Button>(R.id.postBtn);
        postBtn.setOnClickListener {
            Thread{
                try {
                    var payload = JSONObject().apply {
                        put("title","Boy Shirt");
                        put("brand","Nike");
                        put("price",10.20);
                    }

                    var body = payload.toString().toRequestBody(type);
                    var req = okhttp3.Request.Builder()
                        .url("$host/post")
                        .post(body)
                        .build();
                    http.newCall(req).execute().use {
                        response->
                        Log.i("msg","Data Stored");
                    }

                }catch (e : Exception){
                    var emassage = e.message;
                   Log.i("errormassage","error Is $emassage ");
                }
            }
        }
    }
}