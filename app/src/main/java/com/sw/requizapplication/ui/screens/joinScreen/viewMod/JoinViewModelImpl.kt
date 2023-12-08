package com.sw.requizapplication.ui.screens.joinScreen.viewMod

import androidx.lifecycle.viewModelScope
import com.sw.requizapplication.data.models.Quiz
import com.sw.requizapplication.data.repos.QuizRepo
import com.sw.requizapplication.ui.screens.base.viewMod.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinViewModelImpl @Inject constructor(
    private val quizRepo: QuizRepo
) : BaseViewModel(),JoinViewModel {

    private val _quiz: MutableStateFlow<Quiz> = MutableStateFlow(Quiz(questionTitles = emptyList(), options = emptyList(), answers = emptyList(), time = 0))
    val quiz: StateFlow<Quiz> = _quiz

    override fun getQuiz(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                quizRepo.getQuizById(id)
            }?.let {
                _quiz.value = it
            }
        }
    }

}