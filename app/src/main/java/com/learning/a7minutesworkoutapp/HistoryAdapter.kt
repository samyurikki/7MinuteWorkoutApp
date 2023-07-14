package com.learning.a7minutesworkoutapp

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.learning.a7minutesworkoutapp.databinding.HistoryItemBinding

class HistoryAdapter(private val items:ArrayList<String>):RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> (){

    class HistoryViewHolder(binding: HistoryItemBinding):RecyclerView.ViewHolder(binding.root){
        val llMain = binding.llMain
        val tvDate = binding.tvDate
        val sNum = binding.tvSNo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(HistoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
        return items.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
//        TODO("Not yet implemented")
        val date = items[position]
        holder.tvDate.text = date
        holder.sNum.text = "${position+1}"


        if (position%2==0){
            holder.llMain.setBackgroundColor(ContextCompat.getColor(holder.itemView.context,R.color.lightGray))
        }else{
            holder.llMain.setBackgroundColor(Color.WHITE)
        }
    }
}