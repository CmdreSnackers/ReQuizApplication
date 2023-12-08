package com.sw.requizapplication.ui.screens.quizScreen.viewMod


interface QuizViewModel
{
    fun getQuiz(id: String)
    fun startCountdownTimer(timeLimit: Long)
    fun getCurrentUser()
    fun addResult(result: String, quizId: String)
}