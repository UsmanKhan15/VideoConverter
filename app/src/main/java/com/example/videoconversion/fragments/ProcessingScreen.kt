package com.example.videoconversion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.videoconversion.databinding.FragmentProcessingScreenBinding

class ProcessingScreen : Fragment() {
   private val binding by lazy {
       FragmentProcessingScreenBinding.inflate(layoutInflater)
   }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }
}