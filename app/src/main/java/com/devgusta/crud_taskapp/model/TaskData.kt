package com.devgusta.crud_taskapp.model

import android.os.Parcelable
import com.devgusta.crud_taskapp.fbhelper.FirebaseHelper
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskData(
    var id: String = "",
    var tarefa: String = "",
    var status: Int = 0
): Parcelable{
    init {
        this.id = FirebaseHelper.getDataBase().push().key ?: ""
    }
}
