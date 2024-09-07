package com.example.tu_primer_lambo.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tu_primer_lambo.R
import com.example.tu_primer_lambo.ui.view.fragments.OnboardingFragment
import com.example.tu_primer_lambo.ui.view.fragments.MainMenuFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, OnboardingFragment())
                .commit()
        }
    }

    fun startMainMenu() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainMenuFragment())
            .commit()
    }
}
