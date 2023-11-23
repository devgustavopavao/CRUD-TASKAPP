package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentNewTaskBinding
import com.devgusta.crud_taskapp.util.initToolbar
import com.devgusta.crud_taskapp.util.showBottomSheet


class NewTaskFragment : Fragment() {
    private var _binding: FragmentNewTaskBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
         _binding = FragmentNewTaskBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        getListeners()
    }

    private fun getListeners() {
        binding.btnCreatetask.setOnClickListener {
            val task = binding.editNewtask.text.toString().trim()
            if(task.isEmpty()){
               showBottomSheet(msg = R.string.error_newtask_empty)
            }else{
                Toast.makeText(requireContext(),
                    "Tudo ok",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}