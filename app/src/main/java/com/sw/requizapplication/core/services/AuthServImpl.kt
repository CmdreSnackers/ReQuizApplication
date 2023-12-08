package com.sw.requizapplication.core.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthServImpl(
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
): AuthServ
{
    override suspend fun register(
        email: String,
        pass: String
    ): FirebaseUser?
    {
        val result = auth.createUserWithEmailAndPassword(email,pass).await()
        return result.user
    }

    override suspend fun login(
        email: String,
        pass: String
    ): FirebaseUser?
    {
        val result = auth.signInWithEmailAndPassword(email,pass).await()
        return result.user
    }

    override fun getCurrentUser(): FirebaseUser?
    {
        return auth.currentUser
    }

    override fun logout()
    {
        auth.signOut()
    }
}