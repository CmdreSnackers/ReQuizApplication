package com.sw.requizapplication.ui.screens.base

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.sw.requizapplication.R
import com.sw.requizapplication.ui.screens.base.viewMod.BaseViewModel
import kotlinx.coroutines.launch

abstract class BaseFragment<T: ViewBinding>: Fragment()
{

    abstract val viewModel: BaseViewModel
    lateinit var binding: T
    protected lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        viewModel.onCreate()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        navController = NavHostFragment.findNavController(this)
        setupUiComponents(view)
        setupViewModelObserver(view)
    }

    protected open fun onFragmentResult() {}

    open fun setupUiComponents(view: View) {}

    open fun setupViewModelObserver(view: View)
    {
        lifecycleScope.launch {
            viewModel.error.collect {
                showSnackbar(view,it)
            }
        }

        lifecycleScope.launch {
            viewModel.success.collect {
                showSnackbar(view,it)
            }
        }
    }

    fun showSnackbar(view: View, msg:String,error:Boolean = false) {
        val fragmentView = view
        if (fragmentView != null && isAdded && !isDetached) {

            val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)

            if (error) {
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.red
                    )
                )
            } else {
                snackbar.setBackgroundTint(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.green
                    )
                ).setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.black
                    )
                )
            }
            snackbar.show()
        }
    }
    fun logMsg(msg:String,tag:String = "debugging")
    {
        Log.d(tag,msg)
    }
}