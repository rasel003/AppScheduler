package com.rasel.appscheduler

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rasel.appscheduler.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        setContentView(binding.root)

        val installedApps = getInstalledApps(this)

        var text = ""

        installedApps?.forEachIndexed { index, packageInfo ->
            text += "\n"+ index + packageInfo.packageName
        }

        binding.mainText.text = text
    }


    fun getInstalledApps(ctx: Context): Set<PackageInfo>? {
        val packageManager: PackageManager = ctx.getPackageManager()
        val allInstalledPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        val filteredPackages: MutableSet<PackageInfo> = HashSet()
        val defaultActivityIcon = packageManager.defaultActivityIcon
        for (each in allInstalledPackages) {
            if (ctx.getPackageName().equals(each.packageName)) {
                continue  // skip own app
            }
            try {
                // add only apps with application icon
                val intentOfStartActivity =
                    packageManager.getLaunchIntentForPackage(each.packageName)
                        ?: continue
                val applicationIcon = packageManager.getActivityIcon(intentOfStartActivity)
                if (applicationIcon != null && defaultActivityIcon != applicationIcon) {
                    filteredPackages.add(each)
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Log.i("MyTag", "Unknown package name " + each.packageName)
            }
        }
        return filteredPackages
    }
}