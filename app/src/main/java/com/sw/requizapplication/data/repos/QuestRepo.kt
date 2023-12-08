package com.sw.requizapplication.data.repos

import com.sw.requizapplication.data.models.Quest

interface QuestRepo
{

    suspend fun getQuestionsByQuizId(quizId: String): Quest?

}