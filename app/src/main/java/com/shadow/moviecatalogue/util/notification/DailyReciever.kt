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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.ui.main.MainActivity
import java.util.*

class DailyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(
            context, context.resources.getString(R.string.app_name),
            context.getString(R.string.daily_message), NOTIFICATION_ID
        )
    }

    companion object {
        internal var NOTIFICATION_ID = 1

        fun showNotification(context: Context, title: String, message: String, id: Int) {
            val CHANNEL_ID = "channel_01"
            val CHANNEL_NAME = "Daily Channel"

            val notificationManagerCompat =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                context, id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val builder = NotificationCompat.Builder(context, message)
                .setSmallIcon(R.drawable.ic_sad)
                .setContentTitle(title)
                .setColorized(true)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setAutoCancel(true)
                .setSound(alarmSound)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                val channel =
                    NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_HIGH
                    )

                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
                channel.enableLights(true)
                channel.lightColor = R.color.colorPrimary
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

        fun setAlarm(context: Context, time: String ,type: String, message: String) {
            cancelNotification(context)
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, DailyReceiver::class.java)
            val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            intent.putExtra("message", message)
            intent.putExtra("type", type)
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
            calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
            calendar.set(Calendar.SECOND, 0)

            val pendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, intent, 0)
            Objects.requireNonNull(alarmManager).setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )
        }

    }
}
