package com.sw.requizapplication.ui.screens.signUpScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.sw.requizapplication.R
import com.sw.requizapplication.databinding.FragmentSignUpBinding
import com.sw.requizapplication.ui.screens.base.BaseFragment
import com.sw.requizapplication.ui.screens.signUpScreen.viewMod.SignUpViewModel
import com.sw.requizapplication.ui.screens.signUpScreen.viewMod.SignUpViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    override val viewModel: SignUpViewModelImpl by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val roleOptions = resources.getStringArray(R.array.rolesArray)
        val autoCompleteAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, roleOptions)

        binding.regRole.setAdapter(autoCompleteAdapter)

        binding.regRole.setOnItemClickListener { _, _, position, _ ->
            val selectedRole = roleOptions[position]

        }

        setupUiComponents(view)
        setupViewModelObserver(view)
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)

        binding.run {
            regSubBtn.setOnClickListener {
                viewModel.signUp(
                    regName.text.toString(),
                    regEmail.text.toString(),
                    regPass.text.toString(),
                    conRegPass.text.toString(),
                    regRole.text.toString()
                )
            }

            regToLog.setOnClickListener {
                navController.popBackStack()
            }

        }
    }

    override fun setupViewModelObserver(view: View) {
        super.setupViewModelObserver(view)
        lifecycleScope.launch {
            viewModel.success.collect {
                val action = SignUpFragmentDirections.registerToLogin()
                navController.navigate(action)
            }
        }
    }


}