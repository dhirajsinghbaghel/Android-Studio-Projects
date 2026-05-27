package com.example.recycleview

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleview.adapter.ProductsAdapter
import com.example.recycleview.model.ProductModel

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
        var product = listOf(
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic1),
            ProductModel("Girl's","denim",1256.0,"20% off",R.drawable.pic4),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic3),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic5),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic1),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic1),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic1),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic1),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic1),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic1),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic1),
            ProductModel("men's","denim",09876.0,"20% off",R.drawable.pic1),
        );

        var view = findViewById<RecyclerView>(R.id.recycleView);
        view.layoutManager = LinearLayoutManager(this);
        view.adapter = ProductsAdapter(product);
    }
}