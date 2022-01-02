package com.rasel.appscheduler.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasel.appscheduler.data.db.entities.CurrentAlarm
import com.rasel.appscheduler.data.repositories.UserRepository
import com.rasel.appscheduler.ui.util.AlarmReceiver
import com.rasel.appscheduler.ui.util.ExecutionStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun getAlarmList() = repository.getAlarmList()

    fun deleteSchedule(currentAlarm: CurrentAlarm, context: Context) {

        viewModelScope.launch {
            repository.deleteSchedule(currentAlarm)
        }

        context.let { it1 -> AlarmReceiver().cancelAlarm(
            context = it1,
            packageName = currentAlarm.packageName,
            hour = currentAlarm.hour,
            minute = currentAlarm.minute,
            requestCode = currentAlarm.requestCode
        ) }

    }

    fun updateSchedule(currentAlarm: CurrentAlarm, hour: Int, minute: Int, context: Context) {

        val currentAlarmTemp = CurrentAlarm(
            packageName = currentAlarm.packageName,
            hour = hour,
            minute = minute,
            title = currentAlarm.packageName,
            requestCode = currentAlarm.requestCode,
            status = ExecutionStatus.PENDING.status
        )
        viewModelScope.launch {
            repository.updateSchedule( currentAlarmTemp )
        }

        context.let { it1 -> AlarmReceiver().updateAlarm(
            context = it1,
            packageName = currentAlarmTemp.packageName,
            hour = currentAlarmTemp.hour,
            minute = currentAlarmTemp.minute,
            requestCode = currentAlarmTemp.requestCode
        ) }
    }


    /*private val _unsplashPhoto = MutableLiveData<List<UnsplashPhoto>>()

    val unsplashPhoto : LiveData<List<UnsplashPhoto>> = _unsplashPhoto

    fun getDataFromUnSplash(query: String = "Fruit") {

        viewModelScope.launch {
            try {
                val response = repository.getDataFromUnSplash(query)
                if (response.results.isNotEmpty()) {
                    _unsplashPhoto.value = response.results
                   
                } else {
                    Logger.d("Current user available Response is not successful")
                }
            } catch (e: NoInternetException) {
                Logger.d(e.message!!)
            } catch (e: ApiException) {
                Logger.d(e.message!!)
            } catch (e: Exception) {
                Logger.d(e.message ?: "Exception in bottom catch of current user available leave")
            }
        }
    }*/
}