package com.sw.requizapplication.ui.screens.teachScreen.viewMod

import com.sw.requizapplication.data.models.Quiz
import kotlinx.coroutines.flow.StateFlow


interface TeachViewModel
{
    val quiz: StateFlow<List<Quiz>>

    fun getQuiz()
    fun getCurrentUser()
}