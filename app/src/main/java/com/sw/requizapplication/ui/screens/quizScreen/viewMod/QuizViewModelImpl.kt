package com.sw.requizapplication.ui.screens.quizScreen.viewMod

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.models.Mark
import com.sw.requizapplication.data.models.Quiz
import com.sw.requizapplication.data.models.User
import com.sw.requizapplication.data.repos.MarksRepo
import com.sw.requizapplication.data.repos.QuizRepo
import com.sw.requizapplication.data.repos.UsersRepo
import com.sw.requizapplication.ui.screens.base.viewMod.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModelImpl @Inject constructor (
    private val quizRepo: QuizRepo,
    private val marksRepo: MarksRepo,
    private val authServ: AuthServ,
    private val usersRepo: UsersRepo
) : BaseViewModel(), QuizViewModel {

    private val _quiz: MutableStateFlow<Quiz> = MutableStateFlow(Quiz(
        questionTitles = emptyList(),
        options = emptyList(),
        answers = emptyList(),
        time = -1
    ))
    val quiz: StateFlow<Quiz> = _quiz

    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown", role = "Unknown"))
    val user: StateFlow<User> = _user

    private val _remainingTime = MutableStateFlow<String?>(null)
    val remainingTime: StateFlow<String?> = _remainingTime

    private val _done = MutableSharedFlow<Unit>()
    val done: SharedFlow<Unit> = _done

    init
    {
        getCurrentUser()
    }

    override fun getQuiz(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                quizRepo.getQuizById(id)
            }?.let {
                Log.d("debugging", "$it")
                _quiz.value = it
            }
        }
    }

    // Timer
    override fun startCountdownTimer(timeLimit: Long)
    {
        val countdownTimer = object : CountDownTimer(timeLimit * 60 * 1000, 1000)
        {
            override fun onTick(millisUntilFinished: Long)
            {
                val hours = millisUntilFinished / (60 * 60 * 1000)
                val minutes = (millisUntilFinished % (60 * 60 * 1000)) / (60 * 1000)
                val seconds = (millisUntilFinished % (60 * 1000)) / 1000

                val timeRemaining = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                _remainingTime.value = timeRemaining

            }

            // should total up the result and count unanswered as false
            override fun onFinish()
            {
                viewModelScope.launch {
                    _done.emit(Unit)
                }
            }

        }
        countdownTimer.start()
    }


    override fun getCurrentUser()
    {
        val firebaseUser = authServ.getCurrentUser()
        Log.d("debugging", firebaseUser?.uid.toString())
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler { usersRepo.getUser(it.uid) }?.let {  user ->
                    Log.d("debugging", user.toString())
                    _user.value = user
                }
            }
        }
    }

    override fun addResult(result: String, quizId: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                marksRepo.addResult(
                    Mark(result = result, name = user.value.name, quizId = quizId)
                )
            }

        }
    }



}
