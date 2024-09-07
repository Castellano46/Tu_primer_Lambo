package com.example.tu_primer_lambo.ui.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tu_primer_lambo.data.model.CalendarEntry
import com.example.tu_primer_lambo.data.repository.ExerciseRepository
import java.util.*

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ExerciseRepository(application.applicationContext)
    private val _progress = MutableLiveData<Float>()
    val progress: LiveData<Float> get() = _progress
    private val _calendarEntries = MutableLiveData<List<CalendarEntry>>()
    val calendarEntries: LiveData<List<CalendarEntry>> get() = _calendarEntries

    init {
        loadProgress()
        loadCalendarEntries()
    }

    private fun loadProgress() {
        val progressData = repository.getProgress()
        _progress.value = progressData.progress
    }

    private fun loadCalendarEntries() {
        _calendarEntries.value = repository.getCalendarEntries()
    }

    fun updateProgress(burpees: Int, gymChecked: Boolean) {
        val progressData = repository.getProgress()
        val burpeeProgress = (burpees / 100) * 0.05f
        val gymProgress = if (gymChecked) 0.2f else 0f

        progressData.burpees = burpees
        progressData.gymDone = gymChecked
        progressData.progress += burpeeProgress + gymProgress

        repository.saveProgress(progressData)
        _progress.value = progressData.progress
        repository.markTodayExerciseDone()
    }

    fun resetProgress() {
        repository.resetProgress()
        _progress.value = 0f
    }

    fun resetCalendar() {
        repository.resetCalendar()
        loadCalendarEntries()
    }
}
