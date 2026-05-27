package com.example.volleyapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.volleyapp.adaptor.TaxiAdapter
import com.example.volleyapp.model.Taximodel

class SearchDriverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_driver)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val emptyList = ArrayList<Taximodel>();
        var serchBtn = findViewById<Button>(R.id.serchBtn);
        serchBtn.setOnClickListener {
            var keyword = findViewById<EditText>(R.id.searcharea).text;
            var que = Volley.newRequestQueue(this);
            val url = "http://10.58.215.159:3000/driver/$keyword";
            var jar = JsonArrayRequest(Request.Method.GET,url,null,{
                response->
//                Log.i("result",response.toString());
                for(i in 0 .. response.length()-1){
                    var n = response.getJSONObject(i).getString("name");
                    var m = response.getJSONObject(i).getString("mob");
                    emptyList.add(Taximodel(n,m));
                }
                var adp = TaxiAdapter(emptyList);
                var rv = findViewById<RecyclerView>(R.id.rv);
                rv.layoutManager = LinearLayoutManager(this);
                rv.adapter = adp;

            },{
                error->
//                Log.i("error",error.toString());
                Toast.makeText(this,error.toString(),Toast.LENGTH_LONG).show();
            })

            que.add(jar);


        }
    }
}