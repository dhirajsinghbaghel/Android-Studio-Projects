package com.example.mistribaba

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.mistribaba.adaptor.ServiceAdaptor
import com.example.mistribaba.model.ServiceModel

class DashbordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashbord)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val servicedata = ArrayList<ServiceModel>()
        val req = Volley.newRequestQueue(this)



        val jar = JsonArrayRequest(
            Request.Method.GET,
            userinfo.backendurl + "/api/getService",
            null,
            { response ->
                var data = response;
                for (i in 0 .. data.length()-1) {

                    val sname = response.getJSONObject(i).getString("service_name")
                    val picurl = response.getJSONObject(i).getString("pic_url")
                    val sid = response.getJSONObject(i).getString("_id")

                    servicedata.add(ServiceModel(sid, sname,"",picurl ))
                }

                val adapter = ServiceAdaptor(servicedata)
                val displayServiceList = findViewById<RecyclerView>(R.id.serviceListView);
                 displayServiceList.layoutManager = LinearLayoutManager(this);
                displayServiceList.adapter = adapter
            },
            { error ->
                Toast.makeText(this, "ErrorToDashbord"+error.message.toString(), Toast.LENGTH_LONG).show();
            }
        )
        req.add(jar)
    }
}
