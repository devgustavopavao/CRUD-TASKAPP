package com.devgusta.crud_taskapp.fbhelper

import com.devgusta.crud_taskapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class FirebaseHelper {

    companion object{

        fun getDataBase() = FirebaseDatabase.getInstance().reference

        fun getAuth() = FirebaseAuth.getInstance()

        fun getUserId() = getAuth().uid

        fun isAuth() = getAuth().currentUser != null

        fun getError(error: String) : Int{
            return when{
                error.contains("There is no user record") ->{
                    R.string.acc_not_register_fragment
                }
                error.contains("The email address is badly") ->{
                    R.string.acc_email_invalid_fragment
                }
                error.contains("The password is invalid or the user") ->{
                    R.string.acc_password_invalid_fragment
                }
                error.contains("The email address is already in use") ->{
                    R.string.user_email_in_use_register_fragment
                }
                error.contains("The given password is invalid.") ->{
                    R.string.user_password_more_strong_fragment
                }
                else ->{
                    R.string.error_generic
                }
            }
        }

    }
}