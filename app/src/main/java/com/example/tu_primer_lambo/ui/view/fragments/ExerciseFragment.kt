package com.example.tu_primer_lambo.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.tu_primer_lambo.databinding.FragmentExerciseBinding
import com.example.tu_primer_lambo.ui.viewModels.ExerciseViewModel
import androidx.navigation.fragment.findNavController
import com.example.tu_primer_lambo.R

class ExerciseFragment : Fragment() {

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExerciseViewModel by activityViewModels {
        ViewModelProvider.AndroidViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.numberPickerBurpees.apply {
            minValue = 0
            maxValue = 800
            wrapSelectorWheel = true
        }

        binding.btnSubmit.setOnClickListener {
            val burpees = binding.numberPickerBurpees.value

            val wentToGym = when (binding.radioGroup.checkedRadioButtonId) {
                binding.radioSi.id -> true
                binding.radioNo.id -> false
                else -> {
                    Toast.makeText(context, "Fakin inutil, fuiste al gym o no!?.", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            viewModel.updateProgress(burpees, wentToGym)

            showConfirmationDialog()
        }
    }

    private fun showConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Sus datos han sido guardados")
            .setPositiveButton("Aceptar") { _, _ ->
                findNavController().navigate(R.id.action_exerciseFragment_to_homeFragment)
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

