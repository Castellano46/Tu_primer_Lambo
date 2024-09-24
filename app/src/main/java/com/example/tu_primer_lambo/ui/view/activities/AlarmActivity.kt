package com.example.tu_primer_lambo.ui.view.activities

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.tu_primer_lambo.databinding.ActivityAlarmBinding
import com.example.tu_primer_lambo.data.repository.ExerciseRepository
import java.util.*

class AlarmActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlarmBinding
    private lateinit var repository: ExerciseRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        repository = ExerciseRepository(this)
        setContentView(binding.root)

        binding.btnSetAlarm.setOnClickListener {
            val selectedTime = when {
                binding.radio400.isChecked -> Pair(4, 0) to 0.03f
                binding.radio430.isChecked -> Pair(4, 30) to 0.02f
                binding.radio500.isChecked -> Pair(5, 0) to 0.01f
                else -> null
            }

            selectedTime?.let { (time, progress) ->
                setAlarm(time.first, time.second)
                showTimeRemainingDialog(time.first, time.second, progress)
            }
        }
    }

    private fun setAlarm(hour: Int, minute: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun showTimeRemainingDialog(hour: Int, minute: Int, progress: Float) {
        val currentTime = Calendar.getInstance()
        val alarmTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        val hoursRemaining = alarmTime.get(Calendar.HOUR_OF_DAY) - currentTime.get(Calendar.HOUR_OF_DAY)
        val minutesRemaining = alarmTime.get(Calendar.MINUTE) - currentTime.get(Calendar.MINUTE)
        val totalHoursRemaining = hoursRemaining + (minutesRemaining / 60.0)

        AlertDialog.Builder(this)
            .setTitle("Alarma seleccionada")
            .setMessage("Bro, te quedan ${String.format("%.2f", totalHoursRemaining)} horas para ganar otro día más.")
            .setPositiveButton("Aceptar") { _, _ ->
                updateProgress(progress)
                finish()
            }
            .show()
    }

    private fun updateProgress(progress: Float) {
        val currentProgress = repository.getProgress()
        currentProgress.progress += progress
        repository.saveProgress(currentProgress)
    }
}
