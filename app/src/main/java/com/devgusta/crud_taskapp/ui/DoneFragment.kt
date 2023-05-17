package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.adapter.TaskAdapter
import com.devgusta.crud_taskapp.databinding.FragmentDoneBinding
import com.devgusta.crud_taskapp.model.TaskData
import com.devgusta.crud_taskapp.utils.StateView
import com.devgusta.crud_taskapp.utils.showBottomSheet

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
        viewModel.getTask( )
    }


    private fun checkTasks(task: List<TaskData>) {
        binding.textCarregando.text = if (task.isEmpty()) {
            "Nenhuma tarefa cadastrada."
        } else {
            ""
        }
    }

    private fun oberserveViewModel() {
        viewModel.deletetask.observe(viewLifecycleOwner){stateView ->
            when(stateView){
                is StateView.onLoad ->{
                    binding.progressBarLoading.isVisible = true
                }
                is StateView.onSucess ->{
                    binding.progressBarLoading.isVisible = false
                    Toast.makeText(requireContext(),
                        "Tarefa Deletada", Toast.LENGTH_SHORT).show()

                    val oldList = taskAdapter.currentList
                    val newList = oldList.toMutableList().apply {
                        remove(stateView.data)
                    }
                    taskAdapter.submitList(newList)
                }
                is StateView.onError -> {
                    Toast.makeText(requireContext(),
                        stateView.msg, Toast.LENGTH_SHORT).show()
                    binding.progressBarLoading.isVisible = false
                }
            }
        }
        viewModel.taskList.observe(viewLifecycleOwner) {stateView ->
            when(stateView){
                is StateView.onLoad ->{
                    binding.progressBarLoading.isVisible = true
                }
                is StateView.onSucess ->{
                    val taskList = stateView.data?.filter { it.status == 2 }

                    checkTasks(taskList ?: emptyList())
                    binding.progressBarLoading.isVisible = false
                    taskAdapter.submitList(taskList)
                }
                is StateView.onError -> {
                    Toast.makeText(requireContext(),
                        stateView.msg, Toast.LENGTH_SHORT).show()
                    binding.progressBarLoading.isVisible = false
                }
            }

        }
        viewModel.taskInsert.observe(viewLifecycleOwner){ stateView ->

            when(stateView){
                is StateView.onLoad ->{
                    binding.progressBarLoading.isVisible = true
                }
                is StateView.onSucess ->{
                    binding.progressBarLoading.isVisible = false
                    if(stateView.data?.status == 2){
                        // armazena a lista atual do adapter
                        val oldList = taskAdapter.currentList

                        // Gera uma nova lista apartir da lista antiga ja com a tarefa atualizada
                        val newList = oldList.toMutableList().apply {

                            add(1, stateView.data)
                        }
                        // Armazena a posicao da tarefa a ser atualizada na lista
                        val position = newList.indexOfFirst { it.id == stateView.data.id }

                        // envia a nva lista para o adapter ja atualizada
                        taskAdapter.submitList(newList)
                    }
                }
                is StateView.onError -> {
                    Toast.makeText(requireContext(),
                        stateView.msg, Toast.LENGTH_SHORT).show()
                    binding.progressBarLoading.isVisible = false
                }
            }

        }
        viewModel.taskUpdate.observe(viewLifecycleOwner) { stateView ->

            when(stateView){
                is StateView.onLoad ->{
                    binding.progressBarLoading.isVisible = true
                }
                is StateView.onSucess ->{
                    binding.progressBarLoading.isVisible = false
                    // armazena a lista atual do adapter
                    val oldList = taskAdapter.currentList

                    // Gera uma nova lista apartir da lista antiga ja com a tarefa atualizada
                    val newList = oldList.toMutableList().apply {
                        if(!oldList.contains(stateView.data) && stateView.data?.status == 2 ){
                            add(stateView.data)
                        }
                        if(stateView.data?.status == 2){
                            find { it.id == stateView.data.id }?.tarefa = stateView.data.tarefa
                        }else{
                            remove(stateView.data)
                        }
                    }

                    // Armazena a posicao da tarefa a ser atualizada na lista
                    val position = newList.indexOfFirst { it.id == stateView.data?.id }

                    // envia a nva lista para o adapter ja atualizada
                    taskAdapter.submitList(newList)

                    // Atualiza o item do adapter que foi alterado pela posicao
                    taskAdapter.notifyItemChanged(position)
                }
                is StateView.onError -> {
                    Toast.makeText(requireContext(),
                        stateView.msg, Toast.LENGTH_SHORT).show()
                    binding.progressBarLoading.isVisible = false
                }
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
                    viewModel.deleteTasks(task)
                }

            }

            TaskAdapter.SELECTED_BACK -> {
                task.status = 1
                viewModel.updateTask(task)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}