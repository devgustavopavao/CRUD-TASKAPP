package com.devgusta.crud_taskapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.devgusta.crud_taskapp.data.model.Task
import com.devgusta.crud_taskapp.databinding.ItemTaskBinding

class TaskAdapter(
    private val taskList: List<Task>

) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = taskList[position]
        holder.binding.textTask.text = data.task
    }


    inner class MyViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

}