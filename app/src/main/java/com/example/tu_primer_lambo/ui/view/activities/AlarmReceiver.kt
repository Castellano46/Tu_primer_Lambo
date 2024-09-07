package com.example.tu_primer_lambo.ui.view.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        Toast.makeText(context, "¡Es hora de levantarse!", Toast.LENGTH_SHORT).show()
    }
}