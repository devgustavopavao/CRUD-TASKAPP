package com.devgusta.crud_taskapp.utils

import androidx.fragment.app.Fragment
import com.devgusta.crud_taskapp.R
import com.devgusta.crud_taskapp.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog


fun Fragment.showBottomSheet(
    title: Int? = null,
    btnTitle: Int? = null,
    message: Int,
    onClick: () -> Unit = {}
){


    val bottomSheet = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
    val bottomSheetBinding: BottomSheetBinding = BottomSheetBinding.inflate(
        layoutInflater,null,false
    )
    bottomSheetBinding.textTituloBs.text = getText(title?: R.string.text_bottomSheet_aviso)
    bottomSheetBinding.textAvisoBs.text = getText(message)
    bottomSheetBinding.btnEntendi.text = getText( btnTitle?: R.string.text_bottomSheet_btn_entendi)
    bottomSheetBinding.btnEntendi.setOnClickListener {
        onClick()
        bottomSheet.dismiss()
    }
    bottomSheet.setContentView(bottomSheetBinding.root)
    bottomSheet.show()
}
