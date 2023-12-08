package com.sw.requizapplication.ui.screens.signInScreen.viewMod

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.models.User
import com.sw.requizapplication.data.repos.UsersRepo
import com.sw.requizapplication.ui.screens.base.viewMod.BaseViewModel
import com.sw.requizapplication.ui.screens.signUpScreen.viewMod.SignUpViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModelImpl @Inject constructor(
    private val authServ: AuthServ,
    private val usersRepo: UsersRepo,
): BaseViewModel(), SignInViewModel {

    private val _navigateToStudentDash = MutableSharedFlow<Unit>()
    val navigateToStudentDash: SharedFlow<Unit> get() = _navigateToStudentDash

    private val _navigateToTeacherDash = MutableSharedFlow<Unit>()
    val navigateToTeacherDash: SharedFlow<Unit> get() = _navigateToTeacherDash

    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown", role = "Unknown"))
    val user: StateFlow<User> = _user


    override fun login(email: String, pass: String)
    {
        viewModelScope.launch(Dispatchers.IO) {
            val result = errorHandler {
                authServ.login(email, pass)
            }
            if (result != null) {
                _success.emit("Login Success")
            }
        }
    }


    override fun getCurrentUser()
    {
        val firebaseUser = authServ.getCurrentUser()
        Log.d("debugging", firebaseUser?.uid.toString())
        firebaseUser?.let {
            viewModelScope.launch(Dispatchers.IO) {
                errorHandler { usersRepo.getUser(it.uid) }?.let {  user ->
                    Log.d("debugging", user.toString())
                    _user.value = user
                }
            }
        }
    }


}