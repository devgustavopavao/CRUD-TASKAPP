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
import com.devgusta.crud_taskapp.util.initToolbar
import com.devgusta.crud_taskapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private lateinit var auth: FirebaseAuth
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        getListeners()

    }

    private fun getListeners() {
        binding.textCreate.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createAcountFragment)
        }
        binding.textEsqueceu.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverFragment)
        }
        binding.btnLogin.setOnClickListener {
            val getMail = binding.editEmail.text.toString().trim()
            val getpassword = binding.editPassword.text.toString().trim()

            if (getMail.isEmpty()) {
                showBottomSheet(msg = R.string.error_email_empty)

                binding.editEmail.requestFocus()
            } else if (getpassword.isEmpty()) {
               showBottomSheet(msg = R.string.error_password_empty)
                binding.editPassword.requestFocus()
            } else {
                binding.progressBar.isVisible = true
                loginAccount(getMail,getpassword)
            }
        }

    }

    private fun loginAccount(mail: String, password: String) {
            auth.signInWithEmailAndPassword(mail,password)
                .addOnCompleteListener { result ->
                    if(result.isSuccessful){
                        findNavController().navigate(R.id.action_global_homeFragment)
                    }else{
                        binding.progressBar.isVisible = false
                        Toast.makeText(requireContext(),
                            result.exception?.message.toString(),
                            Toast.LENGTH_SHORT).show()
                    }
                }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}