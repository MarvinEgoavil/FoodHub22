package com.example.foodhub.storage

import android.annotation.SuppressLint
import android.content.Context
import com.example.foodhub.models.User

 /*class SharedPrefManager private constructor(private val mCtx: Context){
    val isLoggedIn: Boolean
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
           // return sharedPreferences.getBoolean().

        }

    val user: User
        get() {
            val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return User(
            //     sharedPreferences.getString("email", ""),
             //    sharedPreferences.getString("pass", "")

            )
        }


    fun saveUser(user: User) {

        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("email", user.email)
        editor.putString("pass", user.password)

        editor.apply()

    }

    fun clear() {
        val sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private val SHARED_PREF_NAME = "my_shared_preff"
        @SuppressLint("StaticFieldLeak")
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mCtx: Context): SharedPrefManager {
            if (mInstance == null) {
                mInstance = SharedPrefManager(mCtx)
            }
            return mInstance as SharedPrefManager
        }
    }

}
*/
