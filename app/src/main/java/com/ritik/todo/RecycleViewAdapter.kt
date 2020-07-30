package com.ritik.todo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "Recycle View"

class todoViewHolder(view: View) : RecyclerView.ViewHolder(view){
    var title: TextView = view.findViewById(R.id.title_show)
}

class RecycleViewAdapter(private var rowData: List<String>) : RecyclerView.Adapter<todoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): todoViewHolder {
        val inflator = LayoutInflater.from(parent.context).inflate(R.layout.formatter, parent, false)
        return todoViewHolder(inflator)
    }

    override fun getItemCount(): Int {
        return if(rowData.isNotEmpty()) rowData.size else 0
    }


    override fun onBindViewHolder(holder: todoViewHolder, position: Int) {
        val currentRow = rowData[position]

        holder.title.setText(currentRow)
    }

    fun getDetails(idIs: List<Int>, titles: List<String>, descriptions: List<String>, position: Int): ArrayList<String>{
        val title = titles[position]
        val description  = descriptions[position]
        val details = ArrayList<String>()

        __id = idIs[position]

        details.add(title)
        details.add(description)

        return details

    }
}