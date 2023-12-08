package com.sw.requizapplication.ui.screens.signInScreen.viewMod

import androidx.lifecycle.ViewModel

interface SignInViewModel
{
    fun login(email: String, pass: String)
    fun getCurrentUser()
}