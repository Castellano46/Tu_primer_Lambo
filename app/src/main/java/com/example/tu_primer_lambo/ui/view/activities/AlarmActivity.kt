package com.example.tu_primer_lambo.ui.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.tu_primer_lambo.R
import com.example.tu_primer_lambo.ui.view.fragments.AlarmFragment

class AlarmActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.alarmFragmentContainer, AlarmFragment())
            }
        }
    }
}
