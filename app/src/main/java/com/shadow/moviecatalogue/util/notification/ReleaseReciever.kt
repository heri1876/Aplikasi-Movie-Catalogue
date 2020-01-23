package com.shadow.moviecatalogue.util.notification

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.config.Injector
import com.shadow.moviecatalogue.data.model.response.ItemMovie
import com.shadow.moviecatalogue.repository.catalogue.CatalogueRepository
import com.shadow.moviecatalogue.ui.detail.MovieDetailActivity
import com.shadow.moviecatalogue.util.Utilities
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@Suppress("DEPRECATION")
class ReleaseReceiver : BroadcastReceiver(){
    internal var NOTIFICATION_ID = 3

    @Inject
    internal lateinit var repository: CatalogueRepository
    private var disposable: Disposable? = null

    override fun onReceive(context: Context, intent: Intent) {
        Injector.obtain(context).inject(this)

        getReleaseMovie(context)
    }

    private fun getReleaseMovie(context: Context) {
        val now = Utilities.getCurrentDate()

        disposable = repository.getDailyMovie(now)
            .subscribe({ response ->
                dispose()
                val item = response.results
                for (i: Int in item.indices) {
                    Log.d("debig", item[i].original_title)
                    showNotification(context, item[i].original_title, item[i].overview, item[i].id, item[i])
                }
            }, {
                dispose()
            })
    }

    fun showNotification(context: Context, title: String?, message: String?, id: Int, item: ItemMovie) {
        val CHANNEL_ID = "channel_02"
        val CHANNEL_NAME = "Release Channel"

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra("MovieDetail", item)
        val pendingIntent = PendingIntent.getActivity(
            context, id, intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(message)
            )
            .setSound(alarmSound)
            .setSmallIcon(R.drawable.ic_movie)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setColorized(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_MAX)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.enableVibration(true)
            channel.lightColor = R.color.colorPrimary
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(CHANNEL_ID)

            Objects.requireNonNull(notificationManagerCompat).createNotificationChannel(channel)
        }

        Objects.requireNonNull(notificationManagerCompat).notify(id, builder.build())
    }

    fun cancelNotification(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0)
        Objects.requireNonNull(alarmManager).cancel(pendingIntent)
    }

    fun setAlarm(context: Context, time: String, type: String, message: String) {
        cancelNotification(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReleaseReceiver::class.java)
        intent.putExtra("message", message)
        intent.putExtra("type", type)
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0)
        Objects.requireNonNull(alarmManager).setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.getTimeInMillis(),
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

    }

    private fun dispose() {
        disposable?.let {
            if (!disposable!!.isDisposed) {
                disposable!!.dispose()
            }

            disposable = null
        }
    }
}
