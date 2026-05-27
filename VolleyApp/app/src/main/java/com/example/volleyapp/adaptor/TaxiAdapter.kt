package com.example.volleyapp.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.volleyapp.R
import com.example.volleyapp.model.Taximodel

class TaxiAdapter(private var DriverList: ArrayList<Taximodel> = ArrayList()) : RecyclerView.Adapter<TaxiAdapter.ViewHolder>() {
    class ViewHolder(ui: View) : RecyclerView.ViewHolder(ui) {
        var showname = ui.findViewById<TextView>(R.id.showname);
        var showMobile = ui.findViewById<TextView>(R.id.showMobile);}

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.driverlist,parent,false);
            return ViewHolder(view);
        }

        override fun onBindViewHolder(
            holder: ViewHolder,
            position: Int
        ) {
            holder.showname.text = DriverList[position].name;
            holder.showMobile.text = DriverList[position].mob;
        }

        override fun getItemCount(): Int {
            return DriverList.size;
        }


    }

