package com.rasel.appscheduler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.rasel.appscheduler.databinding.ActivityMainBinding
import com.rasel.appscheduler.databinding.ActivitySelectedBinding

class SelectedActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_selected) as ActivitySelectedBinding
        setContentView(binding.root)

        binding.fabNewSchedule.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}