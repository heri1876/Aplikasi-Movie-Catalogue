package com.shadow.moviecatalogue.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.config.Injector
import com.shadow.moviecatalogue.data.local.preferences.ReminderPreferences
import com.shadow.moviecatalogue.ui.BaseActivity
import com.shadow.moviecatalogue.util.notification.DailyReceiver
import com.shadow.moviecatalogue.util.notification.NotificationKeys.DAILY_REMINDER
import com.shadow.moviecatalogue.util.notification.NotificationKeys.KEY_DAILY_REMINDER
import com.shadow.moviecatalogue.util.notification.NotificationKeys.KEY_RELEASE_REMINDER
import com.shadow.moviecatalogue.util.notification.NotificationKeys.RELEASE_REMINDER
import com.shadow.moviecatalogue.util.notification.NotificationKeys.TIME_DAILY
import com.shadow.moviecatalogue.util.notification.NotificationKeys.TIME_RELEASE
import com.shadow.moviecatalogue.util.notification.NotificationKeys.TYPE_DAILY
import com.shadow.moviecatalogue.util.notification.NotificationKeys.TYPE_RELEASE
import com.shadow.moviecatalogue.util.notification.ReleaseReceiver
import kotlinx.android.synthetic.main.activity_setting.*
import javax.inject.Inject

class SettingActivity : BaseActivity(), SettingContract {

    @Inject internal lateinit var preferences: ReminderPreferences
    private lateinit var DailyReminder: SharedPreferences
    private lateinit var editorDailyReminder: SharedPreferences.Editor
    private lateinit var ReleaseReminder: SharedPreferences
    private lateinit var editorReleaseReminder: SharedPreferences.Editor
    private lateinit var releaseReceiver: ReleaseReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        Injector.obtain(this).inject(this)

        initComponent(savedInstanceState)
    }

    override fun initComponent(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = resources.getText(R.string.setting)
        releaseReceiver = ReleaseReceiver()
        DailyReminder = getSharedPreferences(DAILY_REMINDER, Context.MODE_PRIVATE)
        editorDailyReminder = DailyReminder.edit()
        ReleaseReminder = getSharedPreferences(RELEASE_REMINDER, Context.MODE_PRIVATE)
        editorReleaseReminder = ReleaseReminder.edit()

        checkPreference()

        switchReleaseReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editorReleaseReminder.putBoolean(KEY_RELEASE_REMINDER, true)
                editorReleaseReminder.apply()
                setReleaseTodayReminder()
            } else {
                editorReleaseReminder.putBoolean(KEY_RELEASE_REMINDER, false)
                editorReleaseReminder.commit()
                removeReleaseTodayReminder()
            }
        }

        switchDailyReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editorDailyReminder.putBoolean(KEY_DAILY_REMINDER, true)
                editorDailyReminder.apply()
                setDailyReminder()
            } else {
                editorDailyReminder.putBoolean(KEY_DAILY_REMINDER, false)
                editorDailyReminder.commit()
                removeDailyReminder()
            }
        }
    }

    override fun checkPreference() {
        DailyReminder = getSharedPreferences(DAILY_REMINDER, Context.MODE_PRIVATE)
        switchDailyReminder.setChecked(DailyReminder.getBoolean(KEY_DAILY_REMINDER, false))
        ReleaseReminder = getSharedPreferences(RELEASE_REMINDER, Context.MODE_PRIVATE)
        switchReleaseReminder.setChecked(ReleaseReminder.getBoolean(KEY_RELEASE_REMINDER, false))
    }

    override fun setDailyReminder() {
        preferences.setTimeDaily(TIME_DAILY)
        preferences.setDailyMessage(resources.getString(R.string.daily_message))
        DailyReceiver.setAlarm(
            this@SettingActivity
            , TIME_DAILY
            , TYPE_DAILY
            , resources.getString(R.string.daily_message)
        )
        onShowToast(getString(R.string.dailyReminderOn))
    }

    override fun removeDailyReminder() {
        DailyReceiver.cancelNotification(this)
        onShowToast(getString(R.string.dailyReminderOff))
    }

    override fun setReleaseTodayReminder() {
        preferences.setTimeRelease(TIME_RELEASE)
        preferences.setReleaseMessage(resources.getString(R.string.release_message))
        releaseReceiver.setAlarm(this
            , TIME_RELEASE
            , TYPE_RELEASE
            , resources.getString(R.string.release_message))
        onShowToast(getString(R.string.releaseReminderOn))
    }

    override fun removeReleaseTodayReminder() {
        releaseReceiver.cancelNotification(this)
        onShowToast(getString(R.string.releaseReminderOff))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onShowToast(message: String) {
        showToast(message)
    }
}