package com.rasel.appscheduler.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.rasel.appscheduler.data.db.entities.CurrentAlarm

@Dao
interface CurrentAlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(currentAlarm: CurrentAlarm)

    @Delete()
    suspend fun delete(currentAlarm: CurrentAlarm)

    /*@Query("SELECT * FROM table_current_alarm WHERE id = :id")
    fun getAlarm(id: Int): LiveData<List<CurrentAlarm>>*/

    @Query("SELECT * FROM table_current_alarm")
    fun getAlarmList(): LiveData<List<CurrentAlarm>>

    @Query("UPDATE table_current_alarm SET status = :status WHERE packageName =:packageName")
    fun updateStatus(packageName: String, status: String)

}
