package com.example.recycleview

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        var title = intent.getStringExtra("title");
        var brand = intent.getStringExtra("brand");
        var productprice = intent.getDoubleExtra("price",0.0).toString();
        var discount = intent.getStringExtra("discount");
        var  pic = intent.getIntExtra("pic",0);
        var editTitle = findViewById<EditText>(R.id.editTitle);
        editTitle.setText(title);
        var editbrand= findViewById<EditText>(R.id.editbrand);
        editbrand.setText(brand);
        var editprice = findViewById<EditText>(R.id.editprice);
         editprice.setText(productprice);
        var editdiscount = findViewById<EditText>(R.id.editdiscount);
        editdiscount.setText(discount);
        var editpic = findViewById<ImageView>(R.id.editimagview);
        editpic.setImageResource(pic);

    }
}