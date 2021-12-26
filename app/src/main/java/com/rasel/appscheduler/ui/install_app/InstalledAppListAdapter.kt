package com.rasel.appscheduler.ui.install_app

import android.content.pm.PackageInfo
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasel.appscheduler.databinding.ListItemInstallAppBinding

class InstalledAppList(
    private val onItemClicked: (PackageInfo) -> Unit
) : ListAdapter<PackageInfo, RecyclerView.ViewHolder>(PlantDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return InstalledAppViewHolder(
            ListItemInstallAppBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val plant = getItem(position)
        (holder as InstalledAppViewHolder).bind(plant, onItemClicked)
    }

    class InstalledAppViewHolder(
        private val binding: ListItemInstallAppBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(packageInfo: PackageInfo, onItemClicked: (PackageInfo) -> Unit) {

            binding.apply {
                this.packageInfo = packageInfo
                executePendingBindings()
            }

            binding.root.setOnClickListener {
                binding.packageInfo?.let { plant ->
                    onItemClicked(plant)
                }
            }

            binding.imgLauncherIcon.setImageDrawable( packageInfo.applicationInfo.loadIcon(binding.photographer.context.packageManager))
        }
    }
}

private class PlantDiffCallback : DiffUtil.ItemCallback<PackageInfo>() {

    override fun areItemsTheSame(oldItem: PackageInfo, newItem: PackageInfo): Boolean {
        return oldItem.packageName == newItem.packageName
    }

    override fun areContentsTheSame(oldItem: PackageInfo, newItem: PackageInfo): Boolean {
        return oldItem.applicationInfo.uid == newItem.applicationInfo.uid
    }
}