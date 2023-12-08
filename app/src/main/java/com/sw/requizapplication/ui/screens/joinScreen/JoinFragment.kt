package com.sw.requizapplication.ui.screens.joinScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sw.requizapplication.R
import com.sw.requizapplication.databinding.FragmentJoinBinding
import com.sw.requizapplication.ui.screens.base.BaseFragment
import com.sw.requizapplication.ui.screens.joinScreen.viewMod.JoinViewModel
import com.sw.requizapplication.ui.screens.joinScreen.viewMod.JoinViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class JoinFragment : BaseFragment<FragmentJoinBinding>()
{

    override val viewModel: JoinViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentJoinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        binding.run {
            joinQuizBtn.setOnClickListener {
                val id =  joinQuizId.text.toString()

                viewModel.getQuiz(id)
                val action = JoinFragmentDirections.joinQuizToQuiz(id)
                navController.navigate(action)
            }
        }
    }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.quiz.collect {
                Log.d("debugging", it.toString())
            }
        }
    }

    private fun showErrorMessage(message: String) {
        Log.d("debugging", "Error: $message")
    }

}