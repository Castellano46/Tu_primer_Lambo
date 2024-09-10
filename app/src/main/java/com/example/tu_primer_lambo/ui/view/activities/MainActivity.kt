package com.example.tu_primer_lambo.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.tu_primer_lambo.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        if (savedInstanceState == null) {
            navController.navigate(R.id.onboardingFragment)
        }
    }

    fun startMainMenu() {
        navController.navigate(R.id.homeFragment)
    }
}
