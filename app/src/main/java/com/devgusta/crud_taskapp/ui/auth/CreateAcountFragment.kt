package com.devgusta.crud_taskapp.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentRecoverBinding
import com.devgusta.crud_taskapp.util.initToolbar


class CreateAcountFragment : Fragment() {
    private var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getListeners()
        initToolbar(binding.toolbar)
    }

    private fun getListeners() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}