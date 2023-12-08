package com.sw.requizapplication.ui.screens.leaderBoard.viewMod

import androidx.lifecycle.ViewModel

interface LeaderBoardViewModel
{
    fun getScore()
    fun getScoreByQuizId(quizId: String)
}