package com.sachin_singh_dighan.learn_kotlin_coroutines.data.local

import com.sachin_singh_dighan.learn_kotlin_coroutines.data.local.entity.User

interface DatabaseHelper {

suspend fun getUsers(): List<User>

suspend fun insertAll(users: List<User>)
}