package com.devgusta.crud_taskapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.data.model.Status
import com.devgusta.crud_taskapp.data.model.Task
import com.devgusta.crud_taskapp.databinding.FragmentNewTaskBinding
import com.devgusta.crud_taskapp.util.initToolbar
import com.devgusta.crud_taskapp.util.showBottomSheet
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class NewTaskFragment : Fragment() {
    private var _binding: FragmentNewTaskBinding? = null
    private val binding get() = _binding!!
    private lateinit var task: Task
    private var newTask: Boolean = true
    private var status: Status = Status.TASK_TODO
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = Firebase.database.reference
        auth = Firebase.auth
        initToolbar(binding.toolbar)
        getListeners()
    }

    private fun getListeners() {
        binding.btnCreatetask.setOnClickListener {
            val data = binding.editNewtask.text.toString().trim()
            if (data.isEmpty()) {
                showBottomSheet(msg = R.string.error_newtask_empty)
            } else {
                getRadio()
                binding.progressBar.isVisible = true
                if (newTask) task = Task()
                task.id = ref.database.reference.push().key ?: ""
                task.task = data
                task.status = status
                saveTask()
            }
        }

    }

    private fun getRadio() {
        binding.radiogroup.setOnCheckedChangeListener { _, id ->

            status = when (id) {
                R.id.opt_todo -> Status.TASK_TODO
                R.id.opt_doing -> Status.TASK_DOING
                else -> Status.TASK_DONE

            }
        }
    }

    private fun saveTask() {
        ref.child("tasks")
            .child(auth.currentUser?.uid ?: "")
            .child(task.id)
            .setValue(task).addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    Toast.makeText(
                        requireContext(), R.string.task_saved,
                        Toast.LENGTH_SHORT
                    ).show()
                    if(newTask){ //nova task
                        findNavController().popBackStack()

                    }else{ //editando task
                        binding.progressBar.isVisible = false
                    }
                } else {
                    showBottomSheet(msg = R.string.error_generic)
                }
            }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}