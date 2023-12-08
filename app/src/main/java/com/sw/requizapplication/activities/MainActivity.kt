package com.sw.requizapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sw.requizapplication.R
import com.sw.requizapplication.core.services.AuthServ
import com.sw.requizapplication.data.repos.UsersRepo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authServ: AuthServ

    @Inject
    lateinit var usersRepo: UsersRepo

    private lateinit var navController: NavController

    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navController = findNavController(R.id.navHostFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        lifecycleScope.launch {
            val currentUser = authServ.getCurrentUser()
            val user = currentUser?.let { usersRepo.getUser(it.uid) }

            bottomNavigationView.setOnItemSelectedListener{
                val id = when(it.itemId) {
                    R.id.home -> if(user?.role == "Student") R.id.studentFragment else R.id.teachFragment
                    R.id.leaderboard -> R.id.leaderBoardFragment
                    R.id.profile -> R.id.profFragment
                    else -> R.id.profFragment
                }
                navController.navigate(id)
                true
            }
        }


        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.signInFragment, R.id.signUpFragment, R.id.quizFragment, R.id.addQuizFragment, R.id.joinFragment -> {
                    bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }
    }
}