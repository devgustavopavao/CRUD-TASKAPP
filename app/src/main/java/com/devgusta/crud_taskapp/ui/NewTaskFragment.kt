package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentNewTaskBinding
import com.devgusta.crud_taskapp.util.initToolbar


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
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}