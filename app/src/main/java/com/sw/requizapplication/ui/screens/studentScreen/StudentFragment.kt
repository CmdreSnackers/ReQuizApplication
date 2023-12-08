package com.sw.requizapplication.ui.screens.studentScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.sw.requizapplication.R
import com.sw.requizapplication.databinding.FragmentStudentBinding
import com.sw.requizapplication.ui.screens.base.BaseFragment
import com.sw.requizapplication.ui.screens.studentScreen.viewMod.StudentViewModel
import com.sw.requizapplication.ui.screens.studentScreen.viewMod.StudentViewModelImpl

class StudentFragment : BaseFragment<FragmentStudentBinding>() {


    override val viewModel: StudentViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {

        binding = FragmentStudentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View)
    {
        super.setupUiComponents(view)

        binding.studentJoinBtn.setOnClickListener {
            val action = StudentFragmentDirections.studentDashToJoinQuiz()
            navController.navigate(action)
        }

    }



}