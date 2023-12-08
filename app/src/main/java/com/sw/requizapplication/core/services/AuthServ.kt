package com.sw.requizapplication.core.services

import com.google.firebase.auth.FirebaseUser

interface AuthServ
{
    suspend fun register(
        email:String,
        pass:String
    ): FirebaseUser?

    suspend fun login(
        email: String,
        pass: String
    ): FirebaseUser?

    fun getCurrentUser(): FirebaseUser?

    fun logout()
}