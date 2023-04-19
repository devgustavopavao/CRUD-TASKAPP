package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.adapter.ViewPagerAdapter
import com.devgusta.crud_taskapp.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configTabLayout()
        auth = Firebase.auth
        initClicks()
    }

    private fun initClicks(){
        binding.mbLogout.setOnClickListener { userLogout() }
    }

    private fun userLogout() {
        auth.signOut()
        binding.mbLogout.isClickable = false
        findNavController().navigate(R.id.action_homeFragment_to_navigation)

    }

    private fun configTabLayout(){
        val adapter = ViewPagerAdapter(requireActivity())
        binding.viewpager.adapter = adapter

        adapter.addFragment(TodoFragment(), "A Fazer")
        adapter.addFragment(DoingFragment(), "Fazendo")
        adapter.addFragment(DoneFragment(), "Feitos")

        binding.viewpager.offscreenPageLimit = adapter.itemCount

        TabLayoutMediator(
            binding.tabLayout,binding.viewpager
        ){tab,position ->
            tab.text = adapter.getTitle(position)

        }.attach()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}