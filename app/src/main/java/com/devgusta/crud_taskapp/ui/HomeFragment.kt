package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.FragmentHomeBinding
import com.devgusta.crud_taskapp.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View  {

        _binding =  FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTab()
    }

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