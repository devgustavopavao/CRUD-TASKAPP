package com.devgusta.crud_taskapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.ItemTaskAdapterBinding
import com.devgusta.crud_taskapp.model.TaskData

class  TaskAdapter(
    private val context: Context,
    private val selectedItem : (TaskData, Int) -> Unit
) : ListAdapter <TaskData,TaskAdapter.MyViewHolder>(DIFF_CALLBACK) {

    companion object {

        val SELECTED_BACK : Int = 0
        val SELECTED_EDIT : Int = 1
        val SELECTED_REMOVE : Int = 2
        val SELECTED_NEXT : Int = 3

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TaskData>(){
            override fun areItemsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
              return oldItem.id == newItem.id && oldItem.tarefa == newItem.tarefa
            }

            override fun areContentsTheSame(oldItem: TaskData, newItem: TaskData): Boolean {
                return oldItem == newItem && oldItem.tarefa == newItem.tarefa
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTaskAdapterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)

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