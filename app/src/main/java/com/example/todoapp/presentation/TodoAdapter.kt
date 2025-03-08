package com.example.todoapp.presentation

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.data.local.ToDoItem
import com.example.todoapp.databinding.ItemTodoBinding

class TodoAdapter(
    private val viewModel: TodoViewModel
) : Adapter<TodoAdapter.MyViewHolder>() {
    class MyViewHolder(val binding : ItemTodoBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemTodoBinding.inflate(
            LayoutInflater.from(parent.context),parent,false)
        )
    }

    var list : List<ToDoItem> = emptyList()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]

        holder.binding.textContent.text = item.task
        holder.binding.textWeekday.text = item.dayOfWeek

        // Reset paint flags before applying strikethrough to avoid duplicate effects
        holder.binding.textContent.paintFlags = 0
        holder.binding.textWeekday.paintFlags = 0

        // Set checkbox checked state
        holder.binding.checkBox.setOnCheckedChangeListener(null) // Prevent unwanted triggers
        holder.binding.checkBox.isChecked = item.isCompleted

        // Apply strikethrough if completed
        if (item.isCompleted) {
            holder.binding.textContent.paintFlags = holder.binding.textContent.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            holder.binding.textWeekday.paintFlags = holder.binding.textWeekday.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        // Handle checkbox click
        holder.binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            val updatedItem = item.copy(isCompleted = isChecked) // Create a new modified item
            viewModel.updateCompletedStatus(updatedItem, isChecked)

            // Instantly update UI before waiting for database update
            if (isChecked) {
                holder.binding.textContent.paintFlags = holder.binding.textContent.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                holder.binding.textWeekday.paintFlags = holder.binding.textWeekday.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                holder.binding.textContent.paintFlags = holder.binding.textContent.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                holder.binding.textWeekday.paintFlags = holder.binding.textWeekday.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        // Handle delete icon click
        holder.binding.imageRemove.setOnClickListener {
            viewModel.delete(item) // Call delete method in ViewModel
        }
    }


    fun updateList(newData : List<ToDoItem>){
        list = newData
        notifyDataSetChanged()
    }
}