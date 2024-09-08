package com.example.tu_primer_lambo.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tu_primer_lambo.databinding.FragmentOnboardingBinding
import com.example.tu_primer_lambo.ui.view.activities.MainActivity

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val story = listOf(
        "Era un día como otro cualquiera, te despiertas con resaca y algo en ti ha cambiado.",
        "Decides que es hora de tomar el control de tu vida. No mas PANZA, no mas conformismo",
        "No más excusas...se acabó ser un fakin mileurista",
        "Sabías que para lograr tu sueño y tener tu primer Lambo, tenías que ser disciplinado.",
        "La clave la tiene el gran Llados, tengo que hacer caso de él y de nadie más.",
        "Cada día cuenta, cada burpee te acerca más a tu meta.",
        "Fakin inutil ¿Estás listo para empezar este reto y ver cómo te transformas masivamente?"
    )
    private var currentStoryIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateStory()

        binding.btnNext.setOnClickListener {
            if (currentStoryIndex < story.size - 1) {
                currentStoryIndex++
                updateStory()
            } else {
                (activity as MainActivity).startMainMenu()
            }
        }
    }

    private fun updateStory() {
        binding.txtStory.text = story[currentStoryIndex]
    }
}