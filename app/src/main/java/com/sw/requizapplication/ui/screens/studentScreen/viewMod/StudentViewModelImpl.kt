package com.sw.requizapplication.ui.screens.studentScreen.viewMod

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.models.User
import com.sw.requizapplication.data.repos.UsersRepo
import com.sw.requizapplication.ui.screens.base.viewMod.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModelImpl @Inject constructor(
    private val authServ: AuthServ,
    private val usersRepo: UsersRepo
): BaseViewModel(), StudentViewModel
{

    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown", role = "Unknown"))
    val user: StateFlow<User> = _user

    init {
        getCurrentUser()
    }

    override fun getCurrentUser() {
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