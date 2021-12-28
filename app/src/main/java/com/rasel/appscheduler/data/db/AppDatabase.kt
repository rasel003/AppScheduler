package com.rasel.appscheduler.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rasel.appscheduler.data.db.dao.CurrentAlarmDao
import com.rasel.appscheduler.data.db.entities.CurrentAlarm

@Database(
    entities = [
        CurrentAlarm::class
    ],
    version = 1
)

//Converter class is used to store and retrieve data in the database when is not storable  in their original format
//@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCurrentAlarmDao(): CurrentAlarmDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "AppDatabase.db"
            ).addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                        }
                    }
                )
                .build()
    }
}