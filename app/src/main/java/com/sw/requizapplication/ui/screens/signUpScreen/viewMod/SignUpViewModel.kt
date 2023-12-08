package com.sw.requizapplication.ui.screens.signUpScreen.viewMod



interface SignUpViewModel
{
    fun signUp(
        name: String,
        email: String,
        pass: String,
        conPass: String,
        role: String
    )
}