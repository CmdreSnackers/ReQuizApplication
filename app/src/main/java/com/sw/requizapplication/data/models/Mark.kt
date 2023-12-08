package com.sw.requizapplication.data.models

data class Mark(
    val id : String = "" ,
    val result: String = "",
    val quizId: String = "",
    val createdAt: Long = System.currentTimeMillis(),
    val name: String = ""
)
{
    fun toHashMap(): HashMap<String, Any?> {
        return hashMapOf(
            "id" to id,
            "result" to result,
            "quizId" to quizId,
            "createdAt" to createdAt,
            "name" to name
        )
    }

    companion object {
        fun fromHashMap(hash: Map<String, Any>): Mark {
            return Mark(
                id = hash["id"].toString(),
                result = hash["result"].toString(),
                quizId = hash["quizId"].toString(),
                createdAt = hash["createdAt"].toString().toLong(),
                name = hash["name"].toString()
            )
        }
    }
}