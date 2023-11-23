package com.devgusta.crud_taskapp.ui.auth

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentRecoverBinding
import com.devgusta.crud_taskapp.util.initToolbar
import com.devgusta.crud_taskapp.util.showBottomSheet


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
        binding.btnCreate.setOnClickListener {
            val getMail = binding.createEmail.text.toString().trim()
            val getPassword = binding.createPassword.text.toString().trim()

            if(getMail.isEmpty()){
              showBottomSheet(msg = R.string.error_email_empty)
                binding.createEmail.requestFocus()
            }else if(getPassword.isEmpty()){
               showBottomSheet(msg = R.string.error_password_empty)
                binding.createPassword.requestFocus()
            }else{
                findNavController().popBackStack()
                Toast.makeText(requireContext(),
                    "Conta criado com sucesso!",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}