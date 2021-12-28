package com.rasel.appscheduler.ui.home

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasel.appscheduler.R
import com.rasel.appscheduler.data.db.entities.CurrentAlarm
import com.rasel.appscheduler.databinding.ListItemHomeBinding
import android.content.pm.PackageManager

import android.graphics.drawable.Drawable
import com.rasel.appscheduler.ui.util.getAppNameFromPkgName
import java.text.SimpleDateFormat
import java.util.*


class HomeAdapter(
    private val homeAdapterListener: HomeAdapterListener
) : ListAdapter<CurrentAlarm, RecyclerView.ViewHolder>(PlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlantViewHolder(
            ListItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentAlarm = getItem(position)
        (holder as PlantViewHolder).bind(currentAlarm, homeAdapterListener)
    }

    class PlantViewHolder(
        private val binding: ListItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(currentAlarm: CurrentAlarm, homeAdapterListener: HomeAdapterListener) {
            binding.apply {
                this.currentAlarm = currentAlarm
                executePendingBindings()
            }

            binding.imgEdit.setOnClickListener {
                binding.currentAlarm?.let { currentAlarm ->
                    showMenu(it, R.menu.menu_home, homeAdapterListener, currentAlarm)
                }
            }

            binding.tvAppName.text = getAppNameFromPkgName(binding.tvAppName.context, currentAlarm.packageName)

            // setting up launcher icon
            try {
                val drawable: Drawable = binding.imgEdit.context.packageManager.getApplicationIcon(currentAlarm.packageName)
                binding.imgLauncherIcon.setImageDrawable(drawable)
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            // get a Calendar object with current time
            val cal: Calendar = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, currentAlarm.hour)
            cal.set(Calendar.MINUTE, currentAlarm.minute)

            val sdf = SimpleDateFormat("hh:mm a")
            val time: String = "Start Time : " + sdf.format(cal.time)

            binding.time.text = time

        }

        private fun showMenu(
            v: View,
            @MenuRes menuRes: Int,
            homeAdapterListener: HomeAdapterListener,
            currentAlarm: CurrentAlarm
        ) {
            val popup = PopupMenu(v.context, v)
            popup.menuInflater.inflate(menuRes, popup.menu)

            popup.setOnMenuItemClickListener { item: MenuItem? ->

                when (item!!.itemId) {
                    R.id.option_edit -> {
                        homeAdapterListener.onEditClicked(currentAlarm)
                    }
                    R.id.option_delete -> {
                        homeAdapterListener.onDeleteClicked(currentAlarm)
                    }
                }

                true
            }

            popup.setOnDismissListener {
                // Respond to popup being dismissed.
            }
            // Show the popup menu.
            popup.show()
        }
    }
}

private class PlantDiffCallback : DiffUtil.ItemCallback<CurrentAlarm>() {

    override fun areItemsTheSame(oldItem: CurrentAlarm, newItem: CurrentAlarm): Boolean {
        return oldItem.packageName == newItem.packageName
    }

    override fun areContentsTheSame(oldItem: CurrentAlarm, newItem: CurrentAlarm): Boolean {
        return oldItem == newItem
    }
}

interface HomeAdapterListener {
    fun onEditClicked(currentAlarm: CurrentAlarm)
    fun onDeleteClicked(currentAlarm: CurrentAlarm)
}
