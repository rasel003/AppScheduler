package com.rasel.appscheduler.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.rasel.appscheduler.R
import com.rasel.appscheduler.databinding.FragmentHomeBinding
import com.rasel.appscheduler.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var adapter: HomeAdapter
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        adapter = HomeAdapter()
        binding.recyclerview.adapter = adapter
    }

    override fun onResume() {
        super.onResume()


        binding.fabNewSchedule.setOnClickListener {
            it.findNavController().navigate(R.id.action_nav_home_to_nav_installed_app)
        }
    }
}