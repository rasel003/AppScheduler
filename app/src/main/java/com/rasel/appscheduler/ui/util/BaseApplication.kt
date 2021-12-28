package com.rasel.appscheduler.ui.util

import android.app.Application
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.rasel.appscheduler.BuildConfig
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        /*if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }*/

        // used this logger throughout the app for logging
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })

    }


}