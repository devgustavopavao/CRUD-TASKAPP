package com.devgusta.crud_taskapp.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.devgusta.crud_taskapp.fbhelper.FirebaseHelper
import com.devgusta.crud_taskapp.model.TaskData
import com.devgusta.crud_taskapp.utils.StateView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TaskViewModel : ViewModel() {
    private val _taskList = MutableLiveData<StateView<List<TaskData>>>()
    val taskList: LiveData<StateView<List<TaskData>>> = _taskList

    private val _deletetask = MutableLiveData<StateView<TaskData>>()
    val deletetask: LiveData<StateView<TaskData>> = _deletetask

    private val _taskInsert = MutableLiveData<StateView<TaskData>>()
    val taskInsert: LiveData<StateView<TaskData>> = _taskInsert

    private val _taskUpdate = MutableLiveData<StateView<TaskData>>()
    val taskUpdate: LiveData<StateView<TaskData>> = _taskUpdate


    fun insertTask(dataTaskData: TaskData){
        _taskInsert.postValue(StateView.onLoad())
        try {
            FirebaseHelper.getDataBase()
                .child("tasks")
                .child(FirebaseHelper.getUserId().toString())
                .child(dataTaskData.id)
                .setValue(dataTaskData).addOnCompleteListener {
                    if(it.isSuccessful){
                       _taskInsert.postValue(StateView.onSucess(dataTaskData))
                    }
                }
        }catch (e: Exception){
            _taskInsert.postValue(StateView.onError(e.message.toString()))
        }
            }
    fun getTask() {

    try {

        _taskList.postValue(StateView.onLoad())
        FirebaseHelper.getDataBase().child("tasks")
            .child(FirebaseHelper.getUserId().toString())
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<TaskData>()
                    for (ds in snapshot.children) {
                        val task = ds.getValue(TaskData::class.java) as TaskData
                            taskList.add(task)
                    }
                    taskList.reverse()
                    _taskList.postValue(StateView.onSucess(taskList))
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.i("TAG", "onCancelled: ")
                }

            })
    }catch (e: Exception){
        _taskList.postValue(StateView.onError(e.message.toString()))
    }

    }
    fun updateTask(task: TaskData){


        try {
            _taskUpdate.postValue(StateView.onLoad())

            val map = mapOf(
                "tarefa" to task.tarefa,
                "status" to task.status
            )
            FirebaseHelper.getDataBase()
                .child("tasks")
                .child(FirebaseHelper.getUserId().toString())
                .child(task.id)
                .updateChildren(map).addOnCompleteListener {
                    if(it.isSuccessful){
                        _taskUpdate.postValue(StateView.onSucess(task))
                    }
                }
        }catch (e : Exception){
            _taskUpdate.postValue(StateView.onError(e.message.toString()))
        }

    }
     fun deleteTasks(task: TaskData) {
         try {
             _deletetask.postValue(StateView.onLoad())

             FirebaseHelper.getDataBase().child("tasks")
                 .child(FirebaseHelper.getUserId().toString())
                 .child(task.id).removeValue().addOnCompleteListener {
                     if (it.isSuccessful) {
                         _deletetask.postValue(StateView.onSucess(task))
                     }
                 }
         }catch (e : Exception){
             _deletetask.postValue(StateView.onError(e.message.toString()))
         }
    }
}
