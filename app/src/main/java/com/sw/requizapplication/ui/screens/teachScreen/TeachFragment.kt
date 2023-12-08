package com.sw.requizapplication.ui.screens.teachScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sw.requizapplication.R
import com.sw.requizapplication.databinding.FragmentTeachBinding
import com.sw.requizapplication.ui.adapters.QuizAdp
import com.sw.requizapplication.ui.screens.base.BaseFragment
import com.sw.requizapplication.ui.screens.teachScreen.viewMod.TeachViewModel
import com.sw.requizapplication.ui.screens.teachScreen.viewMod.TeachViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeachFragment : BaseFragment<FragmentTeachBinding>()
{

    override val viewModel: TeachViewModelImpl by  viewModels()
    private lateinit var adapter: QuizAdp
    private var fileName: String = "DefaultFileName"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentTeachBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View)
    {
        super.setupUiComponents(view)

        setupAdapter()

        binding.teachQuizBtn.setOnClickListener {
            val action = TeachFragmentDirections.teacherDashToAddQuiz()
            navController.navigate(action)
        }

    }


    override fun setupViewModelObserver(view: View)
    {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.quiz.collect {
                adapter.setQuiz(it)
            }
        }

    }


    private fun setupAdapter()
    {
        Log.d("FileName", "Value of file: $fileName")
        adapter = QuizAdp(emptyList(), fileName)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvTeachQuiz.adapter = adapter
        binding.rvTeachQuiz.layoutManager = layoutManager
    }

}