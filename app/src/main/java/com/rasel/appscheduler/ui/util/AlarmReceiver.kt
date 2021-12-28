package com.rasel.appscheduler.ui.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.rasel.appscheduler.data.db.AppDatabase
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    private val TAG = "AlarmReceiver"

    override fun onReceive(context: Context, intent: Intent) {

        Log.d(TAG, "Alarm received : "+intent.`package`)

        val launcherIntent: Intent? = intent.`package`?.let {
            context.packageManager.getLaunchIntentForPackage(
                it
            )
        }
        if (launcherIntent != null) {
            ContextCompat.startActivity(context, launcherIntent, null)
        }

        intent.`package`?.let { AppDatabase.invoke(context).getCurrentAlarmDao().updateStatus(it, ExecutionStatus.STARTED.status) }

    }

    fun setAlarm(
        context: Context,
        packageInfo: PackageInfo,
        hour: Int,
        minute: Int,
        requestCode: Int
    ) {
        Log.d(TAG, "setAlarm: ${packageInfo.packageName}")

        // get a Calendar object with current time
        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, 0)

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.setPackage(packageInfo.packageName)
        val senderIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Get the AlarmManager service
        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, cal.timeInMillis] = senderIntent
    }


    fun updateAlarm(
        context: Context,
        packageName: String,
        hour: Int,
        minute: Int,
        requestCode: Int
    ) {
        Log.d(TAG, "updateAlarm: $packageName")

        // get a Calendar object with current time
        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, 0)

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.setPackage(packageName)
        val senderIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Get the AlarmManager service
        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        alarmManager[AlarmManager.RTC_WAKEUP, cal.timeInMillis] = senderIntent
    }

    fun cancelAlarm(
        context: Context,
        packageName: String,
        hour: Int,
        minute: Int,
        requestCode: Int
    ) {
        Log.d(TAG, "cancelAlarm: $packageName")

        // get a Calendar object with current time
        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)
        cal.set(Calendar.SECOND, 0)


        val intent = Intent(context, AlarmReceiver::class.java)
        intent.setPackage(packageName)
        val senderIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Get the AlarmManager service
        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(senderIntent)


    }
}