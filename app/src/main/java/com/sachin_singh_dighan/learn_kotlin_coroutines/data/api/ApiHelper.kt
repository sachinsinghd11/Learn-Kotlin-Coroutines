package com.sachin_singh_dighan.learn_kotlin_coroutines.data.api

import com.sachin_singh_dighan.learn_kotlin_coroutines.data.model.ApiUser

interface ApiHelper {
    suspend fun getUsers(): List<ApiUser>
    suspend fun getMoreUsers(): List<ApiUser>
    suspend fun getUsersWithError(): List<ApiUser>
}