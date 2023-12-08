package com.sw.requizapplication.core.di


import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.repos.MarksRepo
import com.sw.requizapplication.data.repos.MarksRepoImpl
import com.sw.requizapplication.data.repos.QuestRepo
import com.sw.requizapplication.data.repos.QuestRepoImpl
import com.sw.requizapplication.data.repos.QuizRepo
import com.sw.requizapplication.data.repos.QuizRepoImpl
import com.sw.requizapplication.data.repos.UsersRepo
import com.sw.requizapplication.data.repos.UsersRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoMod
{
    @Provides
    @Singleton
    fun provideUsersRepo(authServ: AuthServ): UsersRepo
    {
        return UsersRepoImpl(authServ = authServ)
    }

    @Provides
    @Singleton
    fun provideMarksRepo(authServ: AuthServ): MarksRepo
    {
        return MarksRepoImpl(authServ = authServ)
    }

    @Provides
    @Singleton
    fun provideQuestRepo(authServ: AuthServ): QuestRepo
    {
        return QuestRepoImpl(authServ = authServ)
    }

    @Provides
    @Singleton
    fun providesQuizRepo(authServ: AuthServ): QuizRepo
    {
        return QuizRepoImpl(authServ = authServ)
    }
}