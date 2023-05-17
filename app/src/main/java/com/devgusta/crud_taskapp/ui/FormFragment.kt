package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentFormBinding
import com.devgusta.crud_taskapp.fbhelper.FirebaseHelper
import com.devgusta.crud_taskapp.model.TaskData
import com.devgusta.crud_taskapp.utils.BaseFragment
import com.devgusta.crud_taskapp.utils.showBottomSheet


class FormFragment : BaseFragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataTaskData: TaskData
    private var newTask: Boolean = true
    private var status : Int = 0
    private val args: FormFragmentArgs by navArgs()
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getArgs()
        onListenerGroup()
    }
    private fun getArgs(){
         args.task.let {
             if(it != null){
                 dataTaskData = it
                 configTask()
             }
         }
    }
    fun onListenerGroup() {
        //buton criar
        binding.btnCriarTarefa.setOnClickListener {
            val tarefa = binding.editTarefa.text.toString().trim()

            if (tarefa.isNotEmpty()) {
                binding.progressBarForm.isVisible = true
                if(newTask) dataTaskData = TaskData()
                dataTaskData.tarefa = tarefa
                dataTaskData.status = status

                if(newTask){
                    viewModel.insertTask(dataTaskData)
                }else{
                    viewModel.updateTask(dataTaskData)

                }
            } else {
               showBottomSheet(message = R.string.formtask_empty)
                binding.editTarefa.requestFocus()
            }
            oberserveViewModel()
        }

        //radio Group recover
        binding.radioGroup.setOnCheckedChangeListener { _, id ->
                status = when(id){
                    R.id.radio_a_fazer -> 0
                    R.id.radio_fazendo -> 1
                    else -> 2
                }

        }
    }
    private fun oberserveViewModel() {
        viewModel.taskUpdate.observe(viewLifecycleOwner) { taskInsert ->
            Toast.makeText(requireContext(),
                R.string.save_task, Toast.LENGTH_SHORT).show()
            binding.progressBarForm.isVisible = false
            hideKeyboard()
        }
        viewModel.taskInsert.observe(viewLifecycleOwner) { taskInsert ->
            Toast.makeText(requireContext(),
                R.string.save_task, Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
    private fun configTask(){
        binding.editTarefa.setText( dataTaskData.tarefa)
        setStatus()
        newTask = false
        binding.btnCriarTarefa.text = getString(R.string.save_task_btn)
        status = dataTaskData.status
        binding.textTitleTask.text = getString(R.string.edit_Form_task)
    }
    private fun setStatus(){
      binding.radioGroup.check(
          when (dataTaskData.status){
              0 -> R.id.radio_a_fazer
              1 -> R.id.radio_fazendo
              else -> R.id.radio_feito
          }
      )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}