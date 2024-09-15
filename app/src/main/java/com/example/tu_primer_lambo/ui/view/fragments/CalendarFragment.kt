package com.example.tu_primer_lambo.ui.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.applandeo.materialcalendarview.EventDay
import com.example.tu_primer_lambo.R
import com.example.tu_primer_lambo.databinding.FragmentCalendarBinding
import com.example.tu_primer_lambo.ui.viewModels.ExerciseViewModel
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExerciseViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.calendarEntries.observe(viewLifecycleOwner) { entries ->

            val eventDays = mutableListOf<EventDay>()

            entries.forEach { entry ->
                val calendar = Calendar.getInstance().apply {
                    time = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(entry.date)
                }

                if (entry.exerciseDone) {
                    val eventIcon = R.drawable.baseline_fitness_center_24
                    eventDays.add(EventDay(calendar, eventIcon, R.color.red))
                }
            }

            binding.calendarView.setEvents(eventDays)
        }

        viewModel.progress.observe(viewLifecycleOwner) { progress ->
            val progressPercent = (progress * 100).toInt()
            binding.progressBar.progress = progressPercent
            binding.tvProgressPercentage.text = "Tu Lambo está al: $progressPercent%"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
