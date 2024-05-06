package com.sachin_singh_dighan.learn_kotlin_coroutines.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.api.ApiHelper
import com.sachin_singh_dighan.learn_kotlin_coroutines.data.local.DatabaseHelper
import com.sachin_singh_dighan.learn_kotlin_coroutines.ui.retrofit.single.SingleNetworkCallViewModel

class ViewModelFactory(private val apiHelper: ApiHelper, private val databaseHelper: DatabaseHelper) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleNetworkCallViewModel::class.java)) {
            return SingleNetworkCallViewModel(apiHelper, databaseHelper) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}