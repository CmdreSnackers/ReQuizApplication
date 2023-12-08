package com.sw.requizapplication.data.repos

import com.sw.requizapplication.data.models.User

interface UsersRepo
{

    suspend fun addUser(user: User)

    suspend fun getUser(uid: String): User?

    suspend fun removeUser()

    suspend fun updateUser(user: User)

}