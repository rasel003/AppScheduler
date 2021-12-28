package com.rasel.appscheduler.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "table_current_alarm")
data class CurrentAlarm(

    val title: String,
    val hour: Int,
    val minute: Int,

    @PrimaryKey(autoGenerate = false)
    val packageName: String
)