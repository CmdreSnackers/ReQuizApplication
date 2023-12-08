package com.sw.requizapplication.data.repos

import com.sw.requizapplication.data.models.Mark
import kotlinx.coroutines.flow.Flow

interface MarksRepo
{
    suspend fun addResult(mark:Mark)

    suspend fun getResult() : Flow<List<Mark>>

    suspend fun getResultByQuizId(quizId: String): Flow<List<Mark>>

}