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
import java.util.*

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
//        val message = "Hellooo, alrm worked ----"
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//        val intent2 = Intent(context, TripNotification::class.java)
//        intent2.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        context.startActivity(intent2)

        val launcherIntent: Intent? = intent.`package`?.let {
            context.packageManager.getLaunchIntentForPackage(
                it
            )
        }
        if (launcherIntent != null) {
            ContextCompat.startActivity(context, launcherIntent, null)
        }
    }

    fun setAlarm(context: Context, plant: PackageInfo, hour: Int, minute: Int) {
        Log.d("Carbon", "Alrm SET !!")

        // get a Calendar object with current time
        val cal: Calendar = Calendar.getInstance()
        cal.set(Calendar.HOUR_OF_DAY, hour)
        cal.set(Calendar.MINUTE, minute)

        // add 30 seconds to the calendar object
        //  cal.add(Calendar.MINUTE, 1)


        val intent = Intent(context, AlarmReceiver::class.java)
        intent.setPackage(plant.packageName)
        val senderIntent = PendingIntent.getBroadcast(
            context,
            192837,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Get the AlarmManager service
        val am = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        am[AlarmManager.RTC_WAKEUP, cal.timeInMillis] = senderIntent
    }
}