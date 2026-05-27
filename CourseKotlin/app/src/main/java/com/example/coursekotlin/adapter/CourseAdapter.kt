package com.example.coursekotlin.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coursekotlin.Modal.CourseModel
import com.example.coursekotlin.R

class CourseAdapter(private var courseList: List<CourseModel>): RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    class ViewHolder (ui:View): RecyclerView.ViewHolder(ui){
    var courseNametag = ui.findViewById<TextView>(R.id.courseName);
    var courseDesctag = ui.findViewById<TextView>(R.id.courseDesc);
    var coursePricetag = ui.findViewById<TextView>(R.id.coursePrice);
    var courseDiscounttag = ui.findViewById<TextView>(R.id.courseDiscount);
    var courseDurationtag = ui.findViewById<TextView>(R.id.courseDuration);
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseAdapter.ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CourseAdapter.ViewHolder, position: Int) {
        var course = courseList[position];
        holder.courseNametag.text = "CourseName:"+course.courseName
    }

    override fun getItemCount(): Int {
        return courseList.size;
    }
}