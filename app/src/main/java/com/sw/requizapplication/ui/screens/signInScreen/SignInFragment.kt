package com.sw.requizapplication.ui.screens.signInScreen

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
import com.sw.requizapplication.databinding.FragmentSignInBinding
import com.sw.requizapplication.databinding.FragmentSignUpBinding
import com.sw.requizapplication.ui.screens.base.BaseFragment
import com.sw.requizapplication.ui.screens.signInScreen.viewMod.SignInViewModel
import com.sw.requizapplication.ui.screens.signInScreen.viewMod.SignInViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>()
{

    override val viewModel: SignInViewModelImpl by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun setupUiComponents(view: View)
    {
        super.setupUiComponents(view)

        binding.logSubBtn.setOnClickListener {
            val email = binding.logEmail.text.toString()
            val password = binding.logPass.text.toString()
            viewModel.login(email, password)
        }

        binding.logToReg.setOnClickListener {
            val action = SignInFragmentDirections.loginToRegister()
            navController.navigate(action)
        }

        lifecycleScope.launch {
            viewModel.navigateToStudentDash.collect {
                val action = SignInFragmentDirections.loginToStudentDash()
                navController.navigate(action)
            }
        }

        lifecycleScope.launch {
            viewModel.navigateToTeacherDash.collect {
                val action = SignInFragmentDirections.loginToTeacherDash()
                navController.navigate(action)
            }
        }

    }


    override fun setupViewModelObserver(view: View)
    {
        super.setupViewModelObserver(view)


        lifecycleScope.launch {
            viewModel.user.collect { user ->
                Log.d("debugging", "User role: ${user.role}")
                val action = when (user.role) {
                    "Student" -> SignInFragmentDirections.loginToStudentDash()
                    "Teacher" -> SignInFragmentDirections.loginToTeacherDash()
                    else -> null
                }
                action?.let { navController.navigate(action) }

            }
        }


        lifecycleScope.launch {
            viewModel.success.collect {
                viewModel.getCurrentUser()
            }
        }
    }

}