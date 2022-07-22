package com.assist.imobilandroidapp

import android.app.Application

class ImobilApp : Application() {

    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        lateinit var instance: ImobilApp
    }
}