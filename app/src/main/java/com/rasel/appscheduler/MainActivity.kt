package com.rasel.appscheduler

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.rasel.appscheduler.databinding.ActivityMainBinding
import com.rasel.appscheduler.ui.util.AlarmReceiver
import dagger.hilt.android.AndroidEntryPoint
import android.content.IntentFilter
import com.rasel.appscheduler.ui.util.app_name


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var alarmReceiver: AlarmReceiver
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        // val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        val appBarConfiguration = AppBarConfiguration(
            setOf(R.id.nav_home, R.id.nav_installed_app)
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

       /* val filter = IntentFilter(app_name)


        alarmReceiver = AlarmReceiver()
        registerReceiver( alarmReceiver,  filter)*/

    }

    override fun onDestroy() {
        super.onDestroy()

//        unregisterReceiver(alarmReceiver)
    }

}