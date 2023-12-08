package com.sw.requizapplication.data.repos

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.models.Quest
import kotlinx.coroutines.tasks.await


class QuestRepoImpl(
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val authServ: AuthServ

): QuestRepo
{
    private fun getDbRef(): CollectionReference
    {

        val firebaseUser = authServ.getCurrentUser()

        return firebaseUser?.uid?.let {

            db.collection("Quiz/$it/quiz")

        } ?: throw Exception("Unauthorized user")
    }


    override suspend fun getQuestionsByQuizId(quizId: String): Quest?
    {

        val query = getDbRef().whereEqualTo("quizId", quizId).get().await()

        return if (!query.isEmpty) {

            val doc = query.documents[0]

            doc.toObject(Quest::class.java)?.copy(id = doc.id)

        } else {
            null
        }
    }
}