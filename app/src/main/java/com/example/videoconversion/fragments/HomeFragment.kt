package com.example.videoconversion.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.videoconversion.R
import com.example.videoconversion.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.constraintLayout.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_selectVideoFragment2)
        }
        return binding.root
    }
}