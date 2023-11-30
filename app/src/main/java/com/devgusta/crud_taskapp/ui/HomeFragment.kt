package com.devgusta.crud_taskapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentHomeBinding
import com.devgusta.crud_taskapp.ui.adapter.ViewPagerAdapter
import com.devgusta.crud_taskapp.util.showBottomSheet
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {

        _binding =  FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        initTab()
        getListeners()
    }

    private fun getListeners() {
        binding.btnLogout.setOnClickListener {
            showBottomSheet( msg = R.string.msg_logout, btnTitle = R.string.btn_exit){
                auth.signOut()
                findNavController().navigate(R.id.action_homeFragment_to_Auth)
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun initTab() {
      val adapter = ViewPagerAdapter(requireActivity())
      binding.viewpager.adapter = adapter
        adapter.addFragment(TodoFragment(),R.string.tab_todo)
        adapter.addFragment(DoingFragment(),R.string.tab_doing)
        adapter.addFragment(DoneFragment(),R.string.tab_done)

        binding.viewpager.offscreenPageLimit = adapter.itemCount
        TabLayoutMediator(binding.tabs,binding.viewpager) { tab, position ->
            tab.text = getString(adapter.getTitle(position))
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}