package com.rasel.appscheduler.ui.install_app

import android.content.pm.PackageInfo
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasel.appscheduler.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InstalledAppViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun addData(packageInfo: PackageInfo, hour: Int, minute: Int, requestCode: Int) {
        viewModelScope.launch {
            repository.addData(packageInfo, hour, minute, requestCode)
        }
    }
    private var currentQueryValue: String? = null
}
