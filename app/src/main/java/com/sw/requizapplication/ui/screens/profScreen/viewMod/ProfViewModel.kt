package com.sw.requizapplication.ui.screens.profScreen.viewMod

import android.net.Uri


interface ProfViewModel
{
    fun getCurrentUser()
    fun updateProfilePic(uri: Uri)
    fun getProfilePicUri()
    fun logout()
}