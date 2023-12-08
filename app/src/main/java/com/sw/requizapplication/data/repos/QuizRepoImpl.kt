package com.sw.requizapplication.data.repos

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.models.Quiz
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class QuizRepoImpl(
    private val authServ: AuthServ,
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
): QuizRepo
{
    private fun getDbRef(): CollectionReference
    {
        val firebaseUser = authServ.getCurrentUser()
        return firebaseUser?.uid?.let {

            db.collection("Quiz")

        } ?: throw Exception("Unauthorized user")
    }

    override suspend fun getAllQuiz() = callbackFlow {
        val listener = getDbRef().addSnapshotListener { value, error ->

            if (error != null)
            {
                throw error
            }

            val quiz = mutableListOf<Quiz>()

            value?.documents?.let { docs ->

                for (doc in docs)
                {
                    doc.data?.let {
                        it["id"] = doc.id
                        quiz.add(Quiz.fromHashMap(it))
                    }
                }

                trySend(quiz)
            }
        }

        awaitClose {
            listener.remove()

        }
    }

    override suspend fun addQuiz(quiz: Quiz)
    {
        getDbRef().document(quiz.QuizId).set(quiz.toHashMap()).await()
    }

    override suspend fun getQuizById(id: String): Quiz?
    {

        val querySnapshot = getDbRef().whereEqualTo("quiz_Id", id).get().await()

        Log.d("debugging", querySnapshot.documents.toString())

        return if (!querySnapshot.isEmpty) {
            val doc = querySnapshot.documents[0]

            doc.data?.let {
                it["id"] = doc.id
                Quiz.fromHashMap(it)
            }

        } else {
            null
        }
    }
}