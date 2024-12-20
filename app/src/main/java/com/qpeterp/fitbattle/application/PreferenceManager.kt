package com.qpeterp.fitbattle.application

import android.content.Context
import android.content.SharedPreferences
import com.qpeterp.fitbattle.domain.model.train.TrainType

class PreferenceManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(FIT_BATTLE_APP, Context.MODE_PRIVATE)

    var token: String by PreferenceDelegate("TOKEN", "")
    var ttsState: Boolean by PreferenceDelegate("TTS", false)
    var trainType: String by PreferenceDelegate("TRAIN", TrainType.PUSH_UP.label)

    fun clearToken() {
        token = ""
        ttsState = false
    }

    companion object {
        private const val FIT_BATTLE_APP = "FIT_BATTLE_APP"
    }

    private inner class PreferenceDelegate<T>(
        private val key: String,
        private val defaultValue: T
    ) {
        operator fun getValue(thisRef: Any?, property: Any?): T {
            return when (defaultValue) {
                is String -> prefs.getString(key, defaultValue as String) as T
                is Boolean -> prefs.getBoolean(key, defaultValue as Boolean) as T
                is Int -> prefs.getInt(key, defaultValue as Int) as T
                else -> throw IllegalArgumentException("Unsupported preference type")
            }
        }

        operator fun setValue(thisRef: Any?, property: Any?, value: T) {
            when (value) {
                is String -> prefs.edit().putString(key, value as String).apply()
                is Boolean -> prefs.edit().putBoolean(key, value as Boolean).apply()
                is Int -> prefs.edit().putInt(key, value as Int).apply()
                else -> throw IllegalArgumentException("Unsupported preference type")
            }
        }
    }
}