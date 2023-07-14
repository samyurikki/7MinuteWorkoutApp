package com.learning.a7minutesworkoutapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.learning.a7minutesworkoutapp.databinding.ItemExerciseLayoutBinding

class ExerciseStatusAdapter(private val list:ArrayList<ExerciseModel>):RecyclerView.Adapter<ExerciseStatusAdapter.MainViewHolder>() {
     inner class MainViewHolder(itemBinding:ItemExerciseLayoutBinding):RecyclerView.ViewHolder(itemBinding.root){
         val item = itemBinding.tvItem
     }

         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
//        TODO("Not yet implemented")
             return MainViewHolder(
                 ItemExerciseLayoutBinding.inflate(
                     LayoutInflater.from(parent.context),
                     parent,
                     false
                 )
             )
         }

         override fun getItemCount(): Int {
//        TODO("Not yet implemented")
             return list.size
         }

         override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
             val currentTask = list[position]
             holder.item.text = currentTask.getId().toString()

             when{
                 currentTask.getIsCompleted()->{
                     holder.item.background = ContextCompat.getDrawable(holder.item.context,R.drawable.item_circular_thin_background)
                     holder.item.setTextColor(Color.parseColor("#212121"))
                 }
                 currentTask.getIsSelected()->{
                     holder.item.background = ContextCompat.getDrawable(holder.item.context,R.drawable.item_circular_color_accent_background)
                     holder.item.setTextColor(Color.parseColor("#f1f1f1"))
                 }
                 else->{
                     holder.item.background = ContextCompat.getDrawable(holder.item.context,R.drawable.item_circular_color_gray_background)
                     holder.item.setTextColor(Color.parseColor("#212121"))
                 }
             }
         }

}