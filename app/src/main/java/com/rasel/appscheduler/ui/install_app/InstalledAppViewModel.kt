package com.rasel.appscheduler.ui.install_app

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasel.appscheduler.data.repositories.UserRepository
import kotlinx.coroutines.flow.Flow

class InstalledAppViewModel @ViewModelInject constructor(
    private val repository: UserRepository
) : ViewModel() {
    private var currentQueryValue: String? = null
}
