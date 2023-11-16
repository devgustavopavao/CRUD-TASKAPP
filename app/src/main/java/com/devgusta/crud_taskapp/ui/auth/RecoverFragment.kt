package com.devgusta.crud_taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentRecover2Binding


class RecoverFragment : Fragment() {
   private var _binding: FragmentRecover2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {
        _binding = FragmentRecover2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}