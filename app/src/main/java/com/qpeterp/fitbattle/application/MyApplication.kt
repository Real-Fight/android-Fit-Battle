package com.qpeterp.fitbattle.application

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceManager

        private lateinit var instance: MyApplication

        fun getContext(): Context {
            return instance
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}