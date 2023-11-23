package com.devgusta.crud_taskapp.util

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

@SuppressLint("RestrictedApi")
fun Fragment.initToolbar(toolbar: Toolbar){
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDefaultDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
}

@SuppressLint("ResourceType")
fun Fragment.showBottomSheet(
    titleDialog: Int? = null,
    btnTitle: Int? = null,
    msg: Int,
    onClick: () ->  Unit = {}
) {
    val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheet)
    val bindingBottomSheet: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater,null,false)

    bindingBottomSheet.textWarning.text = getText(titleDialog ?: R.string.warning)
    bindingBottomSheet.textMessage.text = getText(msg)
    bindingBottomSheet.btnAccept.text = getText(btnTitle ?: R.string.bottomsheet_accept)
    bindingBottomSheet.btnAccept.setOnClickListener {
        onClick()
        bottomSheet.dismiss()
    }
    bottomSheet.setContentView(bindingBottomSheet.root)
    bottomSheet.show()


}