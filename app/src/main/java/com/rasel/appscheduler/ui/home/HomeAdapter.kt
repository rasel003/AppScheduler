package com.rasel.appscheduler.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasel.appscheduler.data.db.entities.CurrentAlarm
import com.rasel.appscheduler.databinding.ListItemHomeBinding

class HomeAdapter : ListAdapter<CurrentAlarm, RecyclerView.ViewHolder>(PlantDiffCallback()) {

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
        val plant = getItem(position)
        (holder as PlantViewHolder).bind(plant)
    }

    class PlantViewHolder(
        private val binding: ListItemHomeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.setClickListener {
                binding.photo?.let { plant ->
                    navigateToPlant(plant, it)
                }
            }
        }

        private fun navigateToPlant(
            plant: CurrentAlarm,
            view: View
        ) {
           /* val direction = PlantListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(plant.plantId)
            view.findNavController().navigate(direction)*/
        }

        fun bind(item: CurrentAlarm) {
            binding.apply {
                photo = item
                executePendingBindings()
            }
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
