package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.data.model.Status
import com.devgusta.crud_taskapp.data.model.Task
import com.devgusta.crud_taskapp.databinding.FragmentDoneBinding
import com.devgusta.crud_taskapp.ui.adapter.TaskAdapter


class DoneFragment : Fragment() {
   private var _binding: FragmentDoneBinding? = null
    private lateinit var taskAdapter: TaskAdapter
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
       _binding = FragmentDoneBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView(getLista())
    }

    private fun initRecycleView(taskList: List<Task>){
        taskAdapter = TaskAdapter(taskList)
        with(binding.rvTask){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }
    private fun getLista() = listOf(
        Task("0","Ser uma pessoa melhor", Status.TASK_DONE),
        Task("1","Não desistir dos meus sonhos", Status.TASK_DONE),
        Task("2","Não deixar nada e nem ninguem interferir nisto", Status.TASK_DONE),

    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}