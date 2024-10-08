package com.example.tu_primer_lambo.ui.view.fragments

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tu_primer_lambo.R
import com.example.tu_primer_lambo.data.repository.ExerciseRepository
import com.example.tu_primer_lambo.databinding.FragmentAlarmBinding
import com.example.tu_primer_lambo.ui.view.activities.AlarmReceiver
import java.util.*

class AlarmFragment : Fragment() {

    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!
    private lateinit var repository: ExerciseRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        repository = ExerciseRepository(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSetAlarm.setOnClickListener {
            val selectedTime = when {
                binding.radio400.isChecked -> Pair(4, 0) to 0.03f
                binding.radio430.isChecked -> Pair(4, 30) to 0.02f
                binding.radio500.isChecked -> Pair(5, 0) to 0.01f
                else -> null
            }

            selectedTime?.let { (time, progress) ->
                Log.d("AlarmFragment", "Setting alarm for $time with progress $progress")
                setAlarm(time.first, time.second)
                showTimeRemainingDialog(time.first, time.second, progress)
            }
        }
    }

    private fun setAlarm(hour: Int, minute: Int) {
        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        Log.d("AlarmFragment", "Alarm set for ${calendar.time}")
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    private fun showTimeRemainingDialog(hour: Int, minute: Int, progress: Float) {
        val currentTime = Calendar.getInstance()
        val alarmTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)

            // Si la alarma es antes del tiempo actual, se ajusta para el día siguiente
            if (before(currentTime)) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        // Cálculo de la diferencia de tiempo en milisegundos y conversión a horas
        val timeDifferenceMillis = alarmTime.timeInMillis - currentTime.timeInMillis
        val totalHoursRemaining = timeDifferenceMillis / (1000.0 * 60.0 * 60.0)

        AlertDialog.Builder(requireContext())
            .setTitle("Alarma seleccionada")
            .setMessage("Bro, te quedan ${String.format("%.2f", totalHoursRemaining)} horas para ganar otro día más.")
            .setPositiveButton("Aceptar") { _, _ ->
                updateProgress(progress)
                Log.d("AlarmFragment", "Dialog accepted, progress updated")
                navigateToMainMenu()
            }
            .setCancelable(false)
            .show()
    }

    private fun updateProgress(progress: Float) {
        val currentProgress = repository.getProgress()
        currentProgress.progress += progress
        repository.saveProgress(currentProgress)
        Log.d("AlarmFragment", "Progress updated: ${currentProgress.progress}")
    }

    private fun navigateToMainMenu() {
        findNavController().navigate(R.id.action_alarmFragment_to_homeFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
