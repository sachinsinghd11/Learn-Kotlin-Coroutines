package com.sachin_singh_dighan.learn_kotlin_coroutines.ui.retrofit.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.api.ApiHelper
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.local.DatabaseHelper
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.local.entity.User
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.model.ApiUser
import com.sachin_singh_dighan.learn_kotlin_coroutines.ui.base.UiState
import kotlinx.coroutines.launch

class SeriesNetworkCallsViewModel(
    private val apiHelper: ApiHelper,
    private val dbHelper: DatabaseHelper
) : ViewModel() {

    private val uiState = MutableLiveData<UiState<List<ApiUser>>>()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        viewModelScope.launch {
            uiState.postValue(UiState.Loading)
            try {
                val usersFromApi = apiHelper.getUsers()
                val moreUsersFromApi = apiHelper.getMoreUsers()
                val allUsersFromApi = mutableListOf<ApiUser>()
                allUsersFromApi.addAll(usersFromApi)
                allUsersFromApi.addAll(moreUsersFromApi)
                uiState.postValue(UiState.Success(allUsersFromApi))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error("Something went wrong"))
            }
        }
    }

    fun getUiState(): LiveData<UiState<List<ApiUser>>> {
        return uiState
    }
}