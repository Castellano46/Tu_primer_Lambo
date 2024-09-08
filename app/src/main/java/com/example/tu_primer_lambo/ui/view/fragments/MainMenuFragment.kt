package com.example.tu_primer_lambo.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tu_primer_lambo.databinding.FragmentMainMenuBinding
import androidx.fragment.app.commit
import com.example.tu_primer_lambo.R

class MainMenuFragment : Fragment() {

    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnExercise.setOnClickListener {
            //requireActivity().supportFragmentManager.commit {
            parentFragmentManager.commit {
                replace(R.id.fragment_container, ExerciseFragment())
                addToBackStack(null)
            }
        }

        binding.btnCalendar.setOnClickListener {
            //requireActivity().supportFragmentManager.commit {
            parentFragmentManager.commit {
                replace(R.id.fragment_container, CalendarFragment())
                addToBackStack(null)
            }
        }

        binding.btnAlarm.setOnClickListener {
            //requireActivity().supportFragmentManager.commit {
            parentFragmentManager.commit {
                replace(R.id.fragment_container, AlarmFragment())
                addToBackStack(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

