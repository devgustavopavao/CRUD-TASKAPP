package com.devgusta.crud_taskapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.devgusta.crud_taskapp.data.model.Status
import com.devgusta.crud_taskapp.data.model.Task
import com.devgusta.crud_taskapp.databinding.ItemTaskBinding

class TaskAdapter(
    private val taskList: List<Task>,
    private val taskSelected: (Task, Int) -> Unit

) : RecyclerView.Adapter<TaskAdapter.MyViewHolder>() {

    companion object  {
            const val SELECTED_BACK : Int = 1
            const val SELECTED_DELETE : Int = 2
            const val SELECTED_EDIT : Int = 3
            const val SELECTED_NEXT : Int = 4


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = taskList[position]
        holder.binding.textTask.text = data.task
        setIndicators(data, holder)
        holder.binding.btnDelete.setOnClickListener { taskSelected(data, SELECTED_DELETE) }
        holder.binding.btnEdit.setOnClickListener { taskSelected(data, SELECTED_EDIT) }
    }

    private fun setIndicators(task: Task, holder: MyViewHolder) {
        when (task.status) {
            Status.TASK_TODO -> {
                holder.binding.btnBack.isVisible = false
                holder.binding.btnNext.setOnClickListener { taskSelected(task, SELECTED_NEXT) }
            }
            Status.TASK_DOING -> {
                holder.binding.btnBack.setOnClickListener { taskSelected(task, SELECTED_BACK) }
                holder.binding.btnNext.setOnClickListener { taskSelected(task, SELECTED_NEXT) }
            }

            Status.TASK_DONE -> {
                holder.binding.btnNext.isVisible = false
                holder.binding.btnBack.setOnClickListener { taskSelected(task, SELECTED_BACK) }
            }


        }
    }


    inner class MyViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

}