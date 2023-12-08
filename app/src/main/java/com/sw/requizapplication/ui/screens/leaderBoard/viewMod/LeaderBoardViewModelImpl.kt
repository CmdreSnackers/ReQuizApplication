package com.sw.requizapplication.ui.screens.leaderBoard.viewMod

import androidx.lifecycle.viewModelScope
import com.sw.requizapplication.data.models.Mark
import com.sw.requizapplication.data.repos.MarksRepo
import com.sw.requizapplication.ui.screens.base.viewMod.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderBoardViewModelImpl @Inject constructor(
    private val marksRepo: MarksRepo
) : BaseViewModel(), LeaderBoardViewModel
{

    private val _score: MutableStateFlow<List<Mark>> = MutableStateFlow(emptyList())
    val score: StateFlow<List<Mark>> = _score

    init {
        getScore()
    }

    override fun getScore()
    {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                marksRepo.getResult()
            }?.collect {
                _score.value = it
            }
        }
    }


    override fun getScoreByQuizId(quizId: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                marksRepo.getResultByQuizId(quizId)
            }?.collect {
//                Log.d("LeaderBoardViewModelImpl", "Scores by Quiz ID: $it")
                _score.value = it
            }
        }
    }


}