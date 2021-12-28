package com.rasel.appscheduler.ui.util

import android.content.Context
import android.content.pm.PackageManager


enum class ExecutionStatus(val status: String) {
    PENDING("pending"),
    STARTED("Started")
}

public fun getAppNameFromPkgName(context: Context, Packagename: String?): String? {
    return try {
        val packageManager: PackageManager = context.packageManager
        val info = packageManager.getApplicationInfo(Packagename!!, PackageManager.GET_META_DATA)
        packageManager.getApplicationLabel(info) as String
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        ""
    }
}