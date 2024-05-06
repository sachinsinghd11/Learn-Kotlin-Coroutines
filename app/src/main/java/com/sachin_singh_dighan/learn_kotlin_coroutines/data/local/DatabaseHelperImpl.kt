package com.sachin_singh_dighan.learn_kotlin_coroutines.data.local

import com.sachin_singh_dighan.learn_kotlin_coroutines.data.local.entity.User

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {
    override suspend fun getUsers(): List<User>  = appDatabase.userDao().getAll()

    override suspend fun insertAll(users: List<User>)  = appDatabase.userDao().insertAll(users)
}