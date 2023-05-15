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
import com.devgusta.crud_taskapp.databinding.FragmentLoginBinding
import com.devgusta.crud_taskapp.fbhelper.FirebaseHelper
import com.devgusta.crud_taskapp.utils.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        configClicks()
    }


    private fun configClicks() {
        binding.textCadastrar.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.textRecover.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverFragment)
        }
        binding.btnLogin.setOnClickListener {
            onDataLogin()
        }
    }

    private fun onDataLogin() {
        val email = binding.editEmail.text.toString().trim()
        val password = binding.editSenha.text.toString().trim()

        if (email.isNotEmpty()) {
            if (password.isNotEmpty()) {
                binding.progressBarLogin.isVisible = true
                loginUser(email, password)
            } else {
                showBottomSheet(message = R.string.password_empty)
            }
        } else {
           showBottomSheet(message = R.string.email_empty)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                } else {
                    binding.progressBarLogin.isVisible = false
                    showBottomSheet(message = FirebaseHelper.getError(
                        task.exception?.message ?: ""
                    ))
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}