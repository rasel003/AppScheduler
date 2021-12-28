package com.rasel.appscheduler.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.rasel.appscheduler.R
import com.rasel.appscheduler.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

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

        adapter = HomeAdapter()
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
}