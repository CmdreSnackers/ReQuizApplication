package com.sw.requizapplication.data.repos

import com.sw.requizapplication.data.models.Quiz
import kotlinx.coroutines.flow.Flow

interface QuizRepo
{

    suspend fun getAllQuiz(): Flow<List<Quiz>>

    suspend fun addQuiz(quiz: Quiz)

    suspend fun getQuizById(id: String): Quiz?
}