package com.assist.imobilandroidapp.storage

import android.content.Context
import android.content.SharedPreferences
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.models.RegisterResponse


class SharedPrefManager private constructor(private val mContext: Context){

    private var sharedPrefs: SharedPreferences = mContext.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE)
    val isLoggedIn: Boolean
        get() {
            return sharedPrefs.getString("token", null) != null
        }

    fun clear() {
        val editor = sharedPrefs.edit()
        editor.clear()
        editor.apply()
    }

    fun saveToken(response: RegisterResponse?) {
        val editor = sharedPrefs.edit()
        editor.putString("token", response?.token)
        editor.apply()
    }

    fun fetchToken(): String? {
        return sharedPrefs.getString("token", null)
    }

    companion object {
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(mContext: Context): SharedPrefManager {
            if(mInstance == null) {
                mInstance = SharedPrefManager(mContext)
            }
            return mInstance as SharedPrefManager
        }
    }
}

