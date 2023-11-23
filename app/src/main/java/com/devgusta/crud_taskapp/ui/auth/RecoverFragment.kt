package com.devgusta.crud_taskapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentRecover2Binding
import com.devgusta.crud_taskapp.util.initToolbar
import com.devgusta.crud_taskapp.util.showBottomSheet


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        getListeners()
    }

    private fun getListeners() {
       binding.btnRecover.setOnClickListener {
           val getMail = binding.recoverEmail.text.toString().trim()

           if(getMail.isEmpty()){
               showBottomSheet(msg = R.string.error_email_empty)
               binding.recoverEmail.requestFocus()
           }else{
               findNavController().popBackStack()
               Toast.makeText(requireContext(),
                   "Email enviado para: $getMail",
                   Toast.LENGTH_LONG).show()
           }
       }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }

}