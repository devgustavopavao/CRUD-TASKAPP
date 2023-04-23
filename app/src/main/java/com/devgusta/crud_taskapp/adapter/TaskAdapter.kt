package com.devgusta.crud_taskapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.ItemTaskAdapterBinding
import com.devgusta.crud_taskapp.model.TaskData

class TaskAdapter(
    private val context: Context,
    private val taskList: List<TaskData>,
    private val selectedItem : (TaskData, Int) -> Unit
) : Adapter<TaskAdapter.MyViewHolder>() {

    companion object {

        val SELECTED_BACK : Int = 0
        val SELECTED_EDIT : Int = 1
        val SELECTED_REMOVE : Int = 2
        val SELECTED_NEXT : Int = 3
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTaskAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = taskList[position]

        holder.binding.textView.text = data.tarefa

        holder.binding.btnEditarTarefa.setOnClickListener { selectedItem(data, SELECTED_EDIT) }
        holder.binding.btnRemoverTarefa.setOnClickListener { selectedItem(data, SELECTED_REMOVE) }

        when(data.status){
            0 ->{
                holder.binding.btnVoltarTarefa.isVisible = false
                holder.binding.btnProximaTarefa.setColorFilter(
                    ContextCompat.getColor(context,R.color.color_doing)
                )

                holder.binding.btnProximaTarefa.setOnClickListener { selectedItem(data, SELECTED_NEXT) }
            }
            1 ->{
                holder.binding.btnVoltarTarefa.setColorFilter(
                    ContextCompat.getColor(context,R.color.color_todo)
                )

                holder.binding.btnProximaTarefa.setColorFilter(
                    ContextCompat.getColor(context,R.color.color_done)
                )
                holder.binding.btnVoltarTarefa.setOnClickListener { selectedItem(data, SELECTED_BACK) }
                holder.binding.btnProximaTarefa.setOnClickListener { selectedItem(data, SELECTED_NEXT) }
            }
            else ->{
                holder.binding.btnProximaTarefa.isVisible = false
                holder.binding.btnVoltarTarefa.setColorFilter(
                    ContextCompat.getColor(context,R.color.color_todo)
                )
                holder.binding.btnVoltarTarefa.setOnClickListener { selectedItem(data, SELECTED_BACK) }
            }
        }
    }

    inner class MyViewHolder(val binding: ItemTaskAdapterBinding) : ViewHolder(binding.root)
}