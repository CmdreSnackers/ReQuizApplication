package com.sw.requizapplication.ui.screens.profScreen.viewMod

import android.net.Uri
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.core.services.StorageServ
import com.sw.requizapplication.data.models.User
import com.sw.requizapplication.data.repos.UsersRepo
import com.sw.requizapplication.ui.screens.base.viewMod.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfViewModelImpl @Inject constructor(
    private val authServ: AuthServ,
    private val usersRepo: UsersRepo,
    private val storageServ: StorageServ
): BaseViewModel(), ProfViewModel {

    private val _user = MutableStateFlow(User(name = "Unknown", email = "Unknown", role = "Unknown"))
    val user: StateFlow<User> = _user

    private val _profileUri = MutableStateFlow<Uri?>(null)
    val profileUri: StateFlow<Uri?> = _profileUri

    private val _finish = MutableSharedFlow<Unit>()
    val finish: SharedFlow<Unit> = _finish


    init {
        getCurrentUser()
        getProfilePicUri()
    }


    override fun logout() {
        authServ.logout()
        viewModelScope.launch {
            _finish.emit(Unit)
        }
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

    override fun updateProfilePic(uri: Uri) {
        user.value.id?.let {
            viewModelScope.launch(Dispatchers.IO) {
                val name = "$it.jpg"
                storageServ.addImage(name, uri)
                getProfilePicUri()
            }
        }
    }

    override fun getProfilePicUri() {
        viewModelScope.launch(Dispatchers.IO) {
            authServ.getCurrentUser()?.uid?.let {
                _profileUri.value = storageServ.getImage("$it.jpg")
            }
        }
    }

}