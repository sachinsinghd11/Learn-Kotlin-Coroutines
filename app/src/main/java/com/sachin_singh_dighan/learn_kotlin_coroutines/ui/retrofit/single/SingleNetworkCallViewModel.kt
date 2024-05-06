package com.sachin_singh_dighan.learn_kotlin_coroutines.ui.retrofit.single

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.api.ApiHelper
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.local.DatabaseHelper
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.model.ApiUser
import com.sachin_singh_dighan.learn_kotlin_coroutines.ui.base.UiState
import kotlinx.coroutines.launch

class SingleNetworkCallViewModel(
    private val apiHelper: ApiHelper,
    private val databaseHelper: DatabaseHelper
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
                uiState.postValue(UiState.Success(usersFromApi))
            } catch (e: Exception) {
                uiState.postValue(UiState.Error(e.toString()))

            }
        }
    }

    fun getUiState(): LiveData<UiState<List<ApiUser>>> {
        return uiState
    }
}