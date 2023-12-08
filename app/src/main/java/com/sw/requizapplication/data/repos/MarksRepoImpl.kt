package com.sw.requizapplication.data.repos

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.models.Mark
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class MarksRepoImpl(

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val authServ: AuthServ

): MarksRepo
{
    private fun getDbRef(): CollectionReference {
        return db.collection("Results")
    }

    override suspend fun addResult(mark: Mark)
    {
        getDbRef().document().set(mark.toHashMap()).await()
    }

    override suspend fun getResult() = callbackFlow {

        val listener = getDbRef().addSnapshotListener { value, error ->

            if (error != null)
            {
                throw error
            }

            val score = mutableListOf<Mark>()

            value?.documents?.let { docs ->

                for (doc in docs)
                {
                    doc.data?.let {
                        it["id"] = doc.id
                        score.add(Mark.fromHashMap(it))
                    }
                }

                trySend(score)
            }
        }
        awaitClose {
            listener.remove()
        }
    }


    override suspend fun getResultByQuizId(quizId: String) = callbackFlow {

        val listener = getDbRef().whereEqualTo("quizId", quizId).addSnapshotListener { value, error ->

            if (error != null)
            {
                throw error
            }

            val score = mutableListOf<Mark>()

            value?.documents?.let { docs ->

                for (doc in docs) {
                    doc.data?.let {
                        it["id"] = doc.id
                        score.add(Mark.fromHashMap(it))
                    }
                }
//

                trySend(score)
            }
        }
        awaitClose {
            listener.remove()
        }
    }
}