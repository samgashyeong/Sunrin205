package com.example.sunrin205.screen.main1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sunrin205.R
import com.example.sunrin205.data.LunchAndDate

class  LunchAndDateAdapter(val DataList:ArrayList<LunchAndDate>): RecyclerView.Adapter<LunchAndDateAdapter.MyViewHolder>(){
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val date  = itemView.findViewById<TextView>(R.id.dateTv)
        val data = itemView.findViewById<TextView>(R.id.dataTv)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_lunch_and_date, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.date.text = DataList[position].date
        holder.data.text = DataList[position].Data
    }
    override fun getItemCount() = 7

}