package com.rasel.appscheduler.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rasel.appscheduler.data.db.entities.CurrentAlarm

@Dao
interface CurrentAlarmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(currentAlarm: CurrentAlarm)

    /*@Query("SELECT * FROM table_current_alarm WHERE id = :id")
    fun getAlarm(id: Int): LiveData<List<CurrentAlarm>>*/

    @Query("SELECT * FROM table_current_alarm")
    fun getAlarmList(): LiveData<List<CurrentAlarm>>

}
