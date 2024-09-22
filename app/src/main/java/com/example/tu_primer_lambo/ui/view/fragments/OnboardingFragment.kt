package com.example.tu_primer_lambo.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tu_primer_lambo.R
import com.example.tu_primer_lambo.databinding.FragmentOnboardingBinding
import com.example.tu_primer_lambo.ui.view.activities.MainActivity

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding
    private val storyImages = listOf(
        R.drawable.onboarding_1,
        R.drawable.onboarding_2,
        R.drawable.onboarding_3,
        R.drawable.onboarding_4,
        R.drawable.onboarding_5,
        R.drawable.onboarding_6,
        R.drawable.onboarding_7
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
            if (currentStoryIndex < storyImages.size - 1) {
                currentStoryIndex++
                updateStory()
            } else {
                (activity as MainActivity).startMainMenu()
            }
        }
    }

    private fun updateStory() {
        binding.imgStory.setImageResource(storyImages[currentStoryIndex])
    }
}