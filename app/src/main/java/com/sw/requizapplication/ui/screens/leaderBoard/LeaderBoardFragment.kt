package com.sw.requizapplication.ui.screens.leaderBoard

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sw.requizapplication.R
import com.sw.requizapplication.databinding.FragmentLeaderBoardBinding
import com.sw.requizapplication.ui.adapters.ScoreAdp
import com.sw.requizapplication.ui.screens.base.BaseFragment
import com.sw.requizapplication.ui.screens.leaderBoard.viewMod.LeaderBoardViewModel
import com.sw.requizapplication.ui.screens.leaderBoard.viewMod.LeaderBoardViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LeaderBoardFragment : BaseFragment<FragmentLeaderBoardBinding>()
{

    override val viewModel: LeaderBoardViewModelImpl by viewModels()
    private lateinit var adapter: ScoreAdp
    private lateinit var categoryAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLeaderBoardBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        setupAdapter()

        categoryAdapter = ArrayAdapter(
            requireContext(),
            androidx.transition.R.layout.support_simple_spinner_dropdown_item,
            emptyList()
        )


        binding.run {
            sortQuizRank.setOnItemClickListener { _, _, position, _ ->
                val selectedQuizId = categoryAdapter.getItem(position)
                if (!selectedQuizId.isNullOrBlank()) {
                    viewModel.getScoreByQuizId(selectedQuizId)
                }
            }
        }



    }

    override fun setupViewModelObserver(view: View)
    {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.score.collect { results ->

                adapter.setScore(results.sortedByDescending { it.result.toInt() })


                val quizIds = results.map { it.quizId }.distinct()



                withContext(Dispatchers.Main) {

                    val newQuizIds = ArrayList<String>(quizIds)
                    categoryAdapter = ArrayAdapter(
                        requireContext(),
                        androidx.transition.R.layout.support_simple_spinner_dropdown_item,
                        newQuizIds
                    )
                    binding.sortQuizRank.setAdapter(categoryAdapter)
                }
            }
        }



    }

    private fun setupAdapter()
    {
        adapter = ScoreAdp(emptyList())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvLeaderBoard.adapter = adapter
        binding.rvLeaderBoard.layoutManager = layoutManager
    }


}