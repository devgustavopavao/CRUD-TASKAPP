package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.data.model.Status
import com.devgusta.crud_taskapp.data.model.Task
import com.devgusta.crud_taskapp.databinding.FragmentTodoBinding
import com.devgusta.crud_taskapp.ui.adapter.TaskAdapter


class TodoFragment : Fragment() {
    private lateinit var taskAdapter: TaskAdapter
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getListeners()
        initRecycleView(getLista())
    }

    private fun initRecycleView(taskList: List<Task>) {
        taskAdapter = TaskAdapter(taskList) { task, opt ->
            optSelected(task, opt)

        }
        with(binding.rvTask) {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun optSelected(task: Task, option: Int) {
        when (option) {
            TaskAdapter.SELECTED_DELETE -> {
                Toast.makeText(requireContext(),
                    "Removido ${task.task}",
                    Toast.LENGTH_SHORT).show()
            }

            TaskAdapter.SELECTED_EDIT -> {
                Toast.makeText(requireContext(),
                    "Editando ${task.task}",
                    Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECTED_NEXT -> {
                Toast.makeText(requireContext(),
                    "NEXT",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getLista() = listOf(
        Task("0", "Caminhada com o linor as 18:00", Status.TASK_TODO),
        Task("1", "Fazer dieta de seg a sex", Status.TASK_TODO),
        Task("2", "Aos sabados comer frutas", Status.TASK_TODO),
        Task("3", "Domingo off para comer", Status.TASK_TODO),
        Task("4", "Entrar pro time do spotfy um dia", Status.TASK_TODO),
    )

    private fun getListeners() {
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_newTaskFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}