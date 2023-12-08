package com.sw.requizapplication.ui.screens.addQuizScreen.viewMod


import kotlinx.coroutines.flow.SharedFlow

interface AddViewModel
{

    val finish: SharedFlow<Unit>

    fun addQuiz(quizId: String, title: String, timer: Long?)

    fun getCurrentUser()

    fun readCsv(lines:List<String>)
}