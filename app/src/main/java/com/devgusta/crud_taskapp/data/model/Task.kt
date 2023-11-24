package com.devgusta.crud_taskapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
     val id: String,
     val task: String
) : Parcelable
