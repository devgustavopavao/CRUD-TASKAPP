package com.devgusta.crud_taskapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devgusta.crud_taskapp.model.TaskData

class TaskViewModel : ViewModel() {

    private val _taskUpdate = MutableLiveData<TaskData>()
    val taskUpdate: LiveData<TaskData> = _taskUpdate

    fun setUptadeTask(task: TaskData){
        _taskUpdate.value = task
    }
}