package com.rasel.appscheduler.ui.home

import androidx.lifecycle.ViewModel
import com.rasel.appscheduler.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun getAlarmList() = repository.getAlarmList();


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