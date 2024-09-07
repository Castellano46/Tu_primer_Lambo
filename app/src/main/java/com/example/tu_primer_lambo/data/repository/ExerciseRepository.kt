package com.example.tu_primer_lambo.data.repository

import android.content.Context
import com.example.tu_primer_lambo.data.model.ExerciseProgress
import com.example.tu_primer_lambo.data.model.CalendarEntry
import java.text.SimpleDateFormat
import java.util.*

class ExerciseRepository(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("exercise_data", Context.MODE_PRIVATE)
    private val calendarFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    fun getProgress(): ExerciseProgress {
        val burpees = sharedPreferences.getInt("burpees", 0)
        val gymDone = sharedPreferences.getBoolean("gymDone", false)
        val progress = sharedPreferences.getFloat("progress", 0f)
        return ExerciseProgress(burpees, gymDone, progress)
    }

    fun saveProgress(progress: ExerciseProgress) {
        sharedPreferences.edit().apply {
            putInt("burpees", progress.burpees)
            putBoolean("gymDone", progress.gymDone)
            putFloat("progress", progress.progress)
            apply()
        }
    }

    fun resetProgress() {
        sharedPreferences.edit().apply {
            putInt("burpees", 0)
            putBoolean("gymDone", false)
            putFloat("progress", 0f)
            apply()
        }
    }

    fun getCalendarEntries(): List<CalendarEntry> {
        val calendarString = sharedPreferences.getString("calendar_entries", "") ?: ""
        return if (calendarString.isNotEmpty()) {
            calendarString.split(";").map {
                val parts = it.split(",")
                CalendarEntry(parts[0], parts[1].toBoolean())
            }
        } else {
            emptyList()
        }
    }

    fun saveCalendarEntry(entry: CalendarEntry) {
        val calendarString = sharedPreferences.getString("calendar_entries", "") ?: ""
        val updatedCalendar = calendarString + "${entry.date},${entry.exerciseDone};"
        sharedPreferences.edit().putString("calendar_entries", updatedCalendar).apply()
    }

    fun resetCalendar() {
        sharedPreferences.edit().putString("calendar_entries", "").apply()
    }

    fun markTodayExerciseDone() {
        val today = calendarFormat.format(Date())
        saveCalendarEntry(CalendarEntry(today, true))
    }
}