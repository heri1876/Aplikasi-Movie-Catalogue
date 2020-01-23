package com.shadow.moviecatalogue.ui.setting

import com.shadow.moviecatalogue.ui.BaseContract

interface SettingContract: BaseContract {

    fun checkPreference()

    fun setDailyReminder()

    fun removeDailyReminder()

    fun setReleaseTodayReminder()

    fun removeReleaseTodayReminder()
}