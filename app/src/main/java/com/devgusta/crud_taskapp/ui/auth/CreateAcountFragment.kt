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
import com.devgusta.crud_taskapp.databinding.FragmentRecoverBinding
import com.devgusta.crud_taskapp.util.initToolbar
import com.devgusta.crud_taskapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class CreateAcountFragment : Fragment() {
    private var _binding: FragmentRecoverBinding? = null
    private lateinit var auth: FirebaseAuth
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
        auth = Firebase.auth
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
                binding.progressBar.isVisible = true
                 createAccount(getMail,getPassword)
            }
        }
    }
    private fun createAccount(email: String, senha: String){
        auth.createUserWithEmailAndPassword(email,senha)
            .addOnCompleteListener { result ->
                if(result.isSuccessful){
                    findNavController().navigate(R.id.action_global_homeFragment)

                }else{
                    Toast.makeText(requireContext(),
                        result.exception?.message.toString(), Toast.LENGTH_SHORT).show()
                    binding.progressBar.isVisible = false
                }
            }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}