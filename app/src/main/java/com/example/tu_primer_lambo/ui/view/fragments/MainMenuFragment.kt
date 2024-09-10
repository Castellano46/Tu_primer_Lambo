package com.example.tu_primer_lambo.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tu_primer_lambo.R
import com.example.tu_primer_lambo.databinding.FragmentMainMenuBinding

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
            findNavController().navigate(R.id.action_homeFragment_to_exerciseFragment)
        }

        binding.btnCalendar.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_calendarFragment)
        }

        binding.btnAlarm.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_alarmFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
