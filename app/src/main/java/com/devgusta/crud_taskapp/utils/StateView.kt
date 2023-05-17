package com.devgusta.crud_taskapp.utils

sealed class StateView<T>(
    val data: T? = null,
    val msg: String? = null
) {

    class onLoad<T> : StateView<T>() {}
    class onSucess<T>(data: T, msg: String? = null) : StateView<T>(data, msg)
    class onError<T>(msg: String? = null) : StateView<T>(null, msg)
}
