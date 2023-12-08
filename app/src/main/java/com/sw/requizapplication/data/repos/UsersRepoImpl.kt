package com.sw.requizapplication.data.repos

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.models.User
import kotlinx.coroutines.tasks.await

class UsersRepoImpl(
    private val authServ: AuthServ,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): UsersRepo
{
    private fun getDbRef(): CollectionReference
    {
        return db.collection("users")
    }

    private fun getUid(): String
    {
        val firebaseUser = authServ.getCurrentUser()

        return firebaseUser?.uid ?: throw Exception("Unauthorized user")
    }

    override suspend fun addUser(user: User)
    {
        getDbRef().document(getUid()).set(user.toHashMap()).await()
    }

    override suspend fun getUser(uid: String): User?
    {
        val doc = getDbRef().document(getUid()).get().await()

        return doc.data?.let {
            it["id"] = getUid()
            User.fromHashMap(it)
        }
    }

    override suspend fun removeUser()
    {
        getDbRef()
    }

    override suspend fun updateUser(user: User)
    {
        getDbRef().document(getUid()).set(user.toHashMap())
    }
}