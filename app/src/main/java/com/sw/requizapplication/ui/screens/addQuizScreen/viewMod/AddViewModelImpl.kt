package com.sw.requizapplication.ui.screens.addQuizScreen.viewMod

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.models.Quest
import com.sw.requizapplication.data.models.Quiz
import com.sw.requizapplication.data.models.User
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
class AddViewModelImpl @Inject constructor(
    private val authServ: AuthServ,
    private val quizRepo: QuizRepo,
    private val usersRepo: UsersRepo,
) : BaseViewModel(), AddViewModel {
    private val _finish = MutableSharedFlow<Unit>()
    override val finish: SharedFlow<Unit> = _finish

    private val _quizQuestion = MutableStateFlow<List<Quest>>(emptyList())
    val quizQuestion: StateFlow<List<Quest>> = _quizQuestion

    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown", role = "Unknown"))
    val user: StateFlow<User> = _user

    init {
        getCurrentUser()
    }

    override fun addQuiz(QuizId: String, title: String,  timer: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            val questionTitles = mutableListOf<String>()
            val options = mutableListOf<String>()
            val answers = mutableListOf<String>()
            _quizQuestion.value.map {
                questionTitles.add(it.quest)
                options.add(it.ans1)
                options.add(it.ans2)
                options.add(it.ans3)
                options.add(it.ans4)
                answers.add(it.finalAns)
            }
            errorHandler {
                quizRepo.addQuiz(
                    Quiz(QuizId = QuizId,
                        title = title,
                        questionTitles = questionTitles,
                        options = options,
                        answers = answers,
                        creator = user.value.name,
                        time = timer!!)
                )
            }
            _finish.emit(Unit)
        }
    }

    override fun getCurrentUser() {
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

    override fun readCsv(lines: List<String>) {
        viewModelScope.launch {
            try {
                lines.map { line ->
                    val title = line.split(",")
                    Quest(
                        quest = title[0],
                        ans1 = title[1],
                        ans2 = title[2],
                        ans3 = title[3],
                        ans4 = title[4],
                        finalAns = title[5]
                    )
                }.toList().let {
                    Log.d("debugging", "Questions: $it")
                    if (it.all { true }) {
                        _quizQuestion.emit(it)
                        _success.emit("CSV Import Successful")
                        Log.d("debugging", "CSV Import Successful: ${it.size} questions imported.")
                    } else {
                        Log.e("debugging", "CSV Import Failed: Null values found in QuizQuestions.")
                    }
                }
            } catch (e: Exception) {
                Log.e("debugging", "Error parsing CSV file: ${e.message}")
            }
        }
    }

}