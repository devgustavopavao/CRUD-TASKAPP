package com.devgusta.crud_taskapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
     var id: String = "",
     var task: String = "",
     var status: Status = Status.TASK_TODO
) : Parcelable
