package com.sw.requizapplication.ui.screens.profScreen

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.sw.requizapplication.R
import com.sw.requizapplication.databinding.FragmentProfBinding
import com.sw.requizapplication.ui.screens.base.BaseFragment
import com.sw.requizapplication.ui.screens.profScreen.viewMod.ProfViewModel
import com.sw.requizapplication.ui.screens.profScreen.viewMod.ProfViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfFragment : BaseFragment<FragmentProfBinding>()
{

    override val viewModel: ProfViewModelImpl by viewModels()
    lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        pickMedia = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ) { uri ->
            if (uri != null) {
                viewModel.updateProfilePic(uri)
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    }


    override fun setupUiComponents(view: View)
    {
        super.setupUiComponents(view)



        binding.logoutBtn.setOnClickListener {
            viewModel.logout()
        }

        binding.editPic.setOnClickListener {
            pickMedia.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        }

    }


    override fun setupViewModelObserver(view: View)
    {
        super.setupViewModelObserver(view)

        lifecycleScope.launch {
            viewModel.user.collect {
                binding.profUserName.text = it.name
            }
        }

        lifecycleScope.launch {
            viewModel.user.collect {
                binding.profUserEmail.text = it.email
            }
        }

        lifecycleScope.launch {
            viewModel.finish.collect {
                val action = ProfFragmentDirections.profileToLogin()
                navController.navigate(action)
            }
        }

        lifecycleScope.launch {
            viewModel.profileUri.collect {
                Glide.with(requireContext())
                    .load(it)
                    .placeholder(R.drawable.ic_person)
                    .into(binding.profilePic)
            }
        }

    }


}