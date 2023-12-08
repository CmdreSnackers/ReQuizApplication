package com.sw.requizapplication.ui.screens.quizScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.sw.requizapplication.R
import com.sw.requizapplication.data.models.Quiz
import com.sw.requizapplication.databinding.FragmentQuizBinding
import com.sw.requizapplication.ui.screens.base.BaseFragment
import com.sw.requizapplication.ui.screens.quizScreen.viewMod.QuizViewModel
import com.sw.requizapplication.ui.screens.quizScreen.viewMod.QuizViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>()
{
    private val args: QuizFragmentArgs by navArgs()
    override val viewModel: QuizViewModelImpl by viewModels()
    private var titleNum = 0
    private var optionNum1 = 0
    private var optionNum2 = 1
    private var optionNum3 = 2
    private var optionNum4 = 3
    private var answerNum = 0

    private var result = 0
    private var selectedAns = ""
    private var correctAns = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View
    {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View)
    {
        super.setupUiComponents(view)
        viewModel.getQuiz(args.quizId)

        binding.finishTitle.setOnClickListener {
            val action = QuizFragmentDirections.quizToDash()
            navController.navigate(action)
        }

        binding.quizNextBtn.setOnClickListener {
            when (binding.quizOpt.checkedRadioButtonId) {
                R.id.ans1 -> selectedAns = binding.ans1.text.toString()
                R.id.ans2 -> selectedAns = binding.ans2.text.toString()
                R.id.ans3 -> selectedAns = binding.ans3.text.toString()
                R.id.ans4 -> selectedAns = binding.ans4.text.toString()
            }

            titleNum += 1
            optionNum1 += 4
            optionNum2 += 4
            optionNum3 += 4
            optionNum4 += 4
            answerNum += 1

            if(selectedAns == correctAns) {
                result += 1
            }


            lifecycleScope.launch {
                viewModel.quiz.collect {
                    val greetingText = "You Scored: "
                    val fullName = "$greetingText${result}/${it.questionTitles.size}"
                    binding.scoreTitle.text = fullName
                }
            }

            lifecycleScope.launch {
                viewModel.quiz.collect {
                    if(titleNum > 0){
                        if(titleNum == it.questionTitles.size){
                            binding.llMainQuest.visibility = View.GONE
                            binding.llQuizResult.visibility = View.VISIBLE
                            viewModel.addResult(result.toString(), it.QuizId)
                        }
                    }
                    updateQuiz(it)
                }
            }


            lifecycleScope.launch {
                viewModel.done.collect {
                    binding.llMainQuest.visibility = View.GONE
                    binding.llQuizResult.visibility = View.VISIBLE
                    viewModel.addResult(result.toString(), viewModel.quiz.value.QuizId)
                    updateQuiz(viewModel.quiz.value)
                }
            }


        }
    }



    override fun setupViewModelObserver(view: View)
    {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                updateQuiz(quiz)

                if (quiz.time > 0L)
                {
                    viewModel.startCountdownTimer(quiz.time)
                }

                if (quiz.time == 0L)
                {
                    Log.d("debugging", "Error entering quiz")
                    val action = QuizFragmentDirections.quizToDash()
                    navController.navigate(action)
                }
            }
        }

        lifecycleScope.launch {
            viewModel.remainingTime.collect { remainingTime ->
                binding.timer.text = "Time remaining: $remainingTime"
            }
        }

    }


    private fun updateQuiz(quiz: Quiz)
    {
        binding.run {
            val questionTitle = quiz.questionTitles.getOrNull(titleNum) ?: ""
            questTitle.text = questionTitle

            // Assuming that options list has at least 4 items
            ans1.text = quiz.options.getOrNull(optionNum1) ?: ""
            ans2.text = quiz.options.getOrNull(optionNum2) ?: ""
            ans3.text = quiz.options.getOrNull(optionNum3) ?: ""
            ans4.text = quiz.options.getOrNull(optionNum4) ?: ""

            correctAns = quiz.answers.getOrNull(answerNum) ?: ""
        }
    }

}