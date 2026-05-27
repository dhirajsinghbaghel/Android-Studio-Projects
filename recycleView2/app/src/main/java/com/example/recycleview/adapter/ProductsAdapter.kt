package com.example.recycleview.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleview.EditActivity
import com.example.recycleview.R
import com.example.recycleview.model.ProductModel

class ProductsAdapter(private var productList: List<ProductModel>): RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {
    class ViewHolder(ui :View): RecyclerView.ViewHolder(ui) {
        var title : TextView = ui.findViewById(R.id.title);
        var brand : TextView = ui.findViewById(R.id.brand);
        var price : TextView = ui.findViewById(R.id.price);
        var discount : TextView = ui.findViewById(R.id.discount);
        var pic : ImageView = ui.findViewById(R.id.pic);
        var editBtn : ImageButton = ui.findViewById(R.id.editButton);
        var deleteBtn : ImageButton = ui.findViewById(R.id.deleteButtton);
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductsAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_products,parent,false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: ProductsAdapter.ViewHolder, position: Int) {
        var product = productList[position];
        holder.title.text = product.title;
        holder.brand.text = product.brand;
        holder.price.text = product.price.toString();
        holder.discount.text = product.discount;
        holder.pic.setImageResource(product.productPic);

        holder.editBtn.setOnClickListener{
//            var editTxt = holder.title.text;
//            Log.i("editTxt","edit $position $editTxt");
            var context = holder.itemView.context;
            var intent = Intent(context, EditActivity::class.java).apply {
                putExtra("title",product.title);
                putExtra("brand",product.brand);
                putExtra("price",product.price);
               putExtra("discount",product.discount);
                putExtra("pic",product.productPic);


            };
            context.startActivity(intent);
        }

        holder.deleteBtn.setOnClickListener{
            Log.i("DeleteAction","delete $position");
        }
    }

    override fun getItemCount(): Int {
        return productList.size;
    }
}
