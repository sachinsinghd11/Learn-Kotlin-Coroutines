package com.sachin_singh_dighan.learn_kotlin_coroutines.data.api

import com.sachin_singh_dighan.learn_kotlin_coroutines.data.model.ApiUser

class ApiHelperImpl(private val apiService: ApiService): ApiHelper {
    override suspend fun getUsers(): List<ApiUser>  = apiService.getUsers()

    override suspend fun getMoreUsers(): List<ApiUser>  = apiService.getMoreUsers()

    override suspend fun getUsersWithError(): List<ApiUser>  = apiService.getUsersWithError()

}