package com.rasel.appscheduler.ui.install_app

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.rasel.appscheduler.databinding.FragmentInstalledAppListBinding
import com.rasel.appscheduler.ui.util.AlarmReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job

@AndroidEntryPoint
class InstalledAppFragment : Fragment() {

    private lateinit var binding: FragmentInstalledAppListBinding
    private var searchJob: Job? = null
    private val viewModel: InstalledAppViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstalledAppListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = InstalledAppList{ packageInfo: PackageInfo ->

            val picker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_12H)
                .setTitleText("Select App StartTime time")
                .build()


            picker.show(childFragmentManager, "rsl")

            picker.addOnPositiveButtonClickListener {
                // call back code
                val hour = picker.hour;
                val minute  = picker.minute;

                viewModel.addData(packageInfo, hour, minute)

                context?.let { it1 -> AlarmReceiver().setAlarm(it1, packageInfo, hour, minute) }

            }
        }


        binding.recyclerview.adapter = adapter
        // searchInstalledApp(args.plantName)

        val installedApps = context?.let { getInstalledApps(it) }

        if (installedApps != null) {
            adapter.submitList(installedApps.toList())
        }


    }

    /*private fun searchInstalledApp(query: String) {
        // Make sure we cancel the previous job before creating a new one
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
            viewModel.searchPictures(query).collectLatest {
                adapter.submitData(it)
            }
        }
    }*/

    private fun getInstalledApps(ctx: Context): Set<PackageInfo>? {
        val packageManager: PackageManager = ctx.packageManager
        val allInstalledPackages = packageManager.getInstalledPackages(PackageManager.GET_META_DATA)
        val filteredPackages: MutableSet<PackageInfo> = HashSet()
        val defaultActivityIcon = packageManager.defaultActivityIcon
        for (each in allInstalledPackages) {
            if (ctx.packageName.equals(each.packageName)) {
                continue  // skip own app
            }
            try {
                // add only apps with application icon
                val intentOfStartActivity = packageManager.getLaunchIntentForPackage(each.packageName) ?: continue
                val applicationIcon = packageManager.getActivityIcon(intentOfStartActivity)
                if (defaultActivityIcon != applicationIcon) {
                    filteredPackages.add(each)
                }
            } catch (e: PackageManager.NameNotFoundException) {
                Log.i("MyTag", "Unknown package name " + each.packageName)
            }
        }
        return filteredPackages;
    }
}
