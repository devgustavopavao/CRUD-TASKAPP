package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentFormBinding
import com.devgusta.crud_taskapp.model.TaskData


class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private lateinit var dataTaskData: TaskData
    private var newTask: Boolean = true
    private var status : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFormBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onListenerGroup()
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
            } else {
                Toast.makeText(requireActivity(), "Digite uma tarefa antes", Toast.LENGTH_SHORT)
                    .show()
                binding.editTarefa.requestFocus()
            }
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

    fun saveTask(){

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}