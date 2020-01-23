package com.shadow.moviecatalogue.data.local.preferences

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

@SuppressLint("CommitPrefEdits")
class ReminderPreferences @Inject constructor(context: Context) {

    private val editor: SharedPreferences.Editor

    init {
        val sharedPreferences = context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    fun setTimeRelease(time: String) {
        editor.putString(KEY_DAILY, time)
        editor.commit()
    }

    fun setReleaseMessage(message: String) {
        editor.putString(KEY_MESSAGE_Release, message)
    }

    fun setTimeDaily(time: String) {
        editor.putString(KEY_DAILY, time)
        editor.commit()
    }

    fun setDailyMessage(message: String) {
        editor.putString(KEY_MESSAGE_DAILY, message)
    }

    companion object {
        private val PREFERENCE = "ReminderPreferences"
        private val KEY_DAILY = "DailyReminder"
        private val KEY_MESSAGE_Release = "MessageRelease"
        private val KEY_MESSAGE_DAILY = "MessageDaily"
    }
}
