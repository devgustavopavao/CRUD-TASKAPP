package com.devgusta.crud_taskapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.adapter.TaskAdapter
import com.devgusta.crud_taskapp.databinding.FragmentDoingBinding
import com.devgusta.crud_taskapp.databinding.FragmentDoneBinding
import com.devgusta.crud_taskapp.databinding.FragmentTodoBinding
import com.devgusta.crud_taskapp.fbhelper.FirebaseHelper
import com.devgusta.crud_taskapp.model.TaskData
import com.devgusta.crud_taskapp.utils.showBottomSheet
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class DoneFragment : Fragment() {
    private var _binding: FragmentDoneBinding? = null
    private lateinit var taskAdapter: TaskAdapter
    private val viewModel: TaskViewModel by activityViewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDoneBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycleView()
        oberserveViewModel()
        getTask()
    }

    private fun getTask() {
        FirebaseHelper.getDataBase().child("tasks")
            .child(FirebaseHelper.getUserId().toString())
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    val taskList = mutableListOf<TaskData>()
                    for (ds in snapshot.children) {
                        val task = ds.getValue(TaskData::class.java) as TaskData
                        if (task.status == 2) {
                            taskList.add(task)
                        }
                    }
                    checkTasks(taskList)
                    taskList.reverse()
                    binding.progressBarLoading.isVisible = false
                    taskAdapter.submitList(taskList)

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        requireContext(),
                        R.string.error_generic,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            })


    }
    private fun checkTasks(task: List<TaskData>) {
        binding.textCarregando.text = if (task.isEmpty()) {
            "Nenhuma tarefa cadastrada."
        } else {
            ""
        }
    }

    private fun oberserveViewModel() {
        viewModel.taskUpdate.observe(viewLifecycleOwner) { updateTask ->
            if (updateTask.status == 2) {

                // armazena a lista atual do adapter
                val oldList = taskAdapter.currentList

                // Gera uma nova lista apartir da lista antiga ja com a tarefa atualizada
                val newList = oldList.toMutableList().apply {

                    find { it.id == updateTask.id }?.tarefa = updateTask.tarefa
                }

                // Armazena a posicao da tarefa a ser atualizada na lista
                val position = newList.indexOfFirst { it.id == updateTask.id }

                // envia a nva lista para o adapter ja atualizada
                taskAdapter.submitList(newList)

                // Atualiza o item do adapter que foi alterado pela posicao
                taskAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun initRecycleView(){
        taskAdapter = TaskAdapter(requireContext()){ task, option ->
            optionSelected(task,option)
        }
        with(binding.rvTask){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }
    private fun optionSelected(task: TaskData, option: Int) {
        when (option) {
            TaskAdapter.SELECTED_EDIT -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFormFragment(task)
                findNavController().navigate(action)
            }

            TaskAdapter.SELECTED_REMOVE -> {
                showBottomSheet(
                    btnTitle = R.string.btn_confirm_delete,
                    message = R.string.confirm_delete
                ) {
                    deleteTasks(task)
                }

            }

            TaskAdapter.SELECTED_BACK -> {
                task.status = 1
                backTask(task)
            }
        }
    }
    private fun deleteTasks(task: TaskData) {
        FirebaseHelper.getDataBase().child("tasks")
            .child(FirebaseHelper.getUserId().toString())
            .child(task.id).removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Tarefa deletada!", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showBottomSheet(
                        message = FirebaseHelper.getError(
                            it.exception?.message ?: ""
                        )
                    )
                }
            }
    }

    private fun backTask(task: TaskData) {
        FirebaseHelper.getDataBase().child("tasks")
            .child(FirebaseHelper.getUserId().toString())
            .child(task.id).setValue(task).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Feito!", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    showBottomSheet(
                        message = FirebaseHelper.getError(
                            it.exception?.message ?: ""
                        )
                    )
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}