package com.example.mistribaba.adaptor

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mistribaba.R
import com.example.mistribaba.model.ServiceModel
import com.example.mistribaba.userinfo
import com.squareup.picasso.Picasso

class ServiceAdaptor(private var serviceList : ArrayList<ServiceModel> = ArrayList()): RecyclerView.Adapter<ServiceAdaptor.ViewHolder>(){
    class ViewHolder(ui: View) : RecyclerView.ViewHolder(ui) {
        var servicePic = ui.findViewById<ImageView>(R.id.showservicePic);
        var serviceName = ui.findViewById<TextView>(R.id.showServicename);
        var serviceRequest = ui.findViewById<Button>(R.id.requesBtn);
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ServiceAdaptor. ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.servicelist,parent,false);
        return ViewHolder(view);
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        var service = serviceList[position];

        Log.i("serviceData",service.toString());
        holder.serviceName.text = service.service_name;
        var purl = userinfo.backendurl+"/"+service.pic_name;
        Picasso.get().load(purl).into(holder.servicePic);

    }

    override fun getItemCount(): Int {
        return serviceList.size;
    }



}


