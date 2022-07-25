package com.assist.imobilandroidapp.storage

import android.content.Context
import android.content.SharedPreferences
import com.assist.imobilandroidapp.ImobilApp
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.models.RegisterResponse
class SharedPrefManager private constructor(private val mContext: Context){


    private var sharedPrefs: SharedPreferences = ImobilApp.instance.getSharedPreferences(mContext.getString(R.string.app_name), Context.MODE_PRIVATE)
    val isLoggedIn: Boolean
        get() {
            return sharedPrefs.getString("token", "")?.isNotEmpty() == true
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

    fun saveName(firstName: String?, lastName: String?) {
        val editor = sharedPrefs.edit()
        editor.putString("fullName", "$firstName $lastName")
        editor.apply()
    }

    fun saveUserId(response: RegisterResponse?) {
        val editor = sharedPrefs.edit()
        editor.putString("userId", response?.userId)
        editor.apply()
    }

    fun logout(){
        val editor = sharedPrefs.edit()
        editor.putString("userId", "")
        editor.putString("token", "")
        editor.apply()
    }

    fun fetchToken(): String? {
        return sharedPrefs.getString("token", "")
    }

    fun fetchName(): String? {
        return sharedPrefs.getString("fullName", "")
    }

    fun fetchUserId(): String? {
        return sharedPrefs.getString("userId", "")
    }

    companion object {
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(): SharedPrefManager {
            if(mInstance == null) {
                mInstance = SharedPrefManager(ImobilApp.instance)
            }
            return mInstance as SharedPrefManager
        }
    }
}