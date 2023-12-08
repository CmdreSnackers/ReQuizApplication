package com.sw.requizapplication.core.di

import com.google.firebase.firestore.FirebaseFirestore
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.core.services.AuthServImpl
import com.sw.requizapplication.core.services.StorageServ
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppMod
{
    @Provides
    @Singleton
    fun provideAuthServ(): AuthServ
    {
        return AuthServImpl()
    }

    @Provides
    @Singleton
    fun provideStorageServ(): StorageServ
    {
        return StorageServ()
    }

    @Provides
    @Singleton
    fun provideFireStore(): FirebaseFirestore
    {
        return FirebaseFirestore.getInstance()
    }

}