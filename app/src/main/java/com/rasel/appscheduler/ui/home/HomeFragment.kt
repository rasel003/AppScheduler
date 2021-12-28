package com.rasel.appscheduler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.rasel.appscheduler.R
import com.rasel.appscheduler.data.db.entities.CurrentAlarm
import com.rasel.appscheduler.databinding.FragmentHomeBinding
import com.rasel.appscheduler.ui.util.AlarmReceiver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeAdapterListener {

    private lateinit var adapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = HomeAdapter(this)
        binding.recyclerview.adapter = adapter

        viewModel.getAlarmList().observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

    }

    override fun onResume() {
        super.onResume()

        binding.fabNewSchedule.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_home_to_nav_installed_app)
        }
    }

    override fun onEditClicked(currentAlarm: CurrentAlarm) {

        val picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setTitleText("Select New StartTime time")
            .build()


        picker.show(childFragmentManager, "rsl")

        picker.addOnPositiveButtonClickListener {
            // call back code
            val hour = picker.hour;
            val minute = picker.minute;

            context?.let { it1 ->
                viewModel.updateSchedule(currentAlarm, hour, minute, it1)
                Toast.makeText(it1, "Schedule Updated", Toast.LENGTH_SHORT).show()

            }
        }
        context?.let { viewModel.deleteSchedule(currentAlarm, it) }

    }

    override fun onDeleteClicked(currentAlarm: CurrentAlarm) {
        context?.let { viewModel.deleteSchedule(currentAlarm, it) }
    }
}