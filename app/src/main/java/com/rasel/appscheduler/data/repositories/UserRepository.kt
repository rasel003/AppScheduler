package com.rasel.appscheduler.data.repositories

import android.content.pm.PackageInfo
import com.rasel.appscheduler.data.db.AppDatabase
import com.rasel.appscheduler.data.db.entities.CurrentAlarm
import com.rasel.appscheduler.ui.util.ExecutionStatus
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val db: AppDatabase
) {

    fun getAlarmList() = db.getCurrentAlarmDao().getAlarmList()

    suspend fun addData(packageInfo: PackageInfo, hour: Int, minute: Int, requestCode: Int) {

        val currentAlarm = CurrentAlarm(
            packageName = packageInfo.packageName,
            hour = hour,
            minute = minute,
            title = packageInfo.packageName,
            requestCode = requestCode,
            status = ExecutionStatus.PENDING.status
        )
        db.getCurrentAlarmDao().upsert(currentAlarm)
    }

    suspend fun deleteSchedule(currentAlarm: CurrentAlarm) {
        db.getCurrentAlarmDao().delete(currentAlarm)
    }

    suspend fun updateSchedule(currentAlarmTemp: CurrentAlarm) {
        db.getCurrentAlarmDao().upsert(currentAlarmTemp)
    }

}