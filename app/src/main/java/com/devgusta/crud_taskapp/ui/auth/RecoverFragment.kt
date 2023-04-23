package com.devgusta.crud_taskapp.ui.auth

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentRecoverBinding
import com.devgusta.crud_taskapp.fbhelper.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app


class RecoverFragment : Fragment() {
    private  var _binding: FragmentRecoverBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecoverBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks() {
        binding.btnRecover.setOnClickListener {
            onData()
        }
    }

    private fun onData() {
        val email = binding.editEmailRecover.text.toString().trim()

        if(email.isNotEmpty()){
            binding.progressBarRecover.isVisible = true
            recoverUserAccount(email)
        }else{
            Toast.makeText(requireContext(), "Digite seu email por favor"
                , Toast.LENGTH_SHORT).show()
        }
    }

    private fun closeFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    private fun recoverUserAccount(email: String) {
       auth.sendPasswordResetEmail(email)
           .addOnCompleteListener { task ->
               if(task.isSuccessful){
                   Toast.makeText(requireContext(), "Enviado para: $email"
                       , Toast.LENGTH_SHORT).show()

                   Handler(Looper.getMainLooper()).postDelayed(this::closeFragment,3000)
               }else{
                 binding.progressBarRecover.isVisible = false
                   Toast.makeText(
                       requireActivity(),
                       FirebaseHelper.getError(task.exception?.message ?: ""),
                       Toast.LENGTH_SHORT
                   ).show()
               }
           }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}