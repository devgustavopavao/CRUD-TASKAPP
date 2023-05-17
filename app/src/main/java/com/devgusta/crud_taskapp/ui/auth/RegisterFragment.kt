package com.devgusta.crud_taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentRegisterBinding
import com.devgusta.crud_taskapp.fbhelper.FirebaseHelper
import com.devgusta.crud_taskapp.utils.BaseFragment
import com.devgusta.crud_taskapp.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : BaseFragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
  override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.btnCadastrar.setOnClickListener { dataUser() }
    }

    private fun dataUser(){
        val email = binding.editEmailCadastro.text.toString().trim()
        val passoword = binding.editSenhaCadastro.text.toString().trim()
        
        if(email.isNotEmpty()){
            if(passoword.isNotEmpty()){
                hideKeyboard()
                binding.progressBar.isVisible = true
                registerUser(email,passoword)
            }else{
                showBottomSheet(message = R.string.password_empty)
            }
        }else{
            showBottomSheet(message = R.string.email_empty)
        }
    }

    private fun registerUser(email: String, passoword: String) {
        auth.createUserWithEmailAndPassword(email,passoword)
            .addOnCompleteListener(requireActivity()){ task->
                if(task.isSuccessful){
                    findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                }else{
                     showBottomSheet(message = FirebaseHelper.getError(
                         task.exception?.message ?: ""
                     ))
                    binding.progressBar.isVisible = false
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}