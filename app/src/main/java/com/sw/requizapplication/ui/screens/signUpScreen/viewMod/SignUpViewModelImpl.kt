package com.sw.requizapplication.ui.screens.signUpScreen.viewMod

import androidx.lifecycle.viewModelScope
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.models.User
import com.sw.requizapplication.data.repos.UsersRepo
import com.sw.requizapplication.ui.screens.base.viewMod.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModelImpl @Inject constructor(
    private val authServ: AuthServ,
    private val usersRepo: UsersRepo
):BaseViewModel(), SignUpViewModel {
    override fun signUp(name: String, email: String, pass: String, confirmPass: String, role: String) {
        viewModelScope.launch {
            val user = errorHandler {
                authServ.register(email, pass)
            }

            if(user != null) {
                _success.emit("Registered Successfully")
                errorHandler {
                    usersRepo.addUser(
                        User(name = name, email = email, role = role)
                    )
                }
            }
        }
    }
}