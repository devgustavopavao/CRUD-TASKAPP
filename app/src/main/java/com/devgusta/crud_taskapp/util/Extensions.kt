package com.devgusta.crud_taskapp.util

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

@SuppressLint("RestrictedApi")
fun Fragment.initToolbar(toolbar: Toolbar){
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
}