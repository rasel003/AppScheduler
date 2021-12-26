package com.rasel.appscheduler.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CurrentAlarm(

    val id: Int,
    val title: String,

    @PrimaryKey(autoGenerate = false)
    val packageName: String
)