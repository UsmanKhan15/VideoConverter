package com.example.videoconversion.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.videoconversion.R
import com.example.videoconversion.Video
import com.example.videoconversion.VideoAdapter
import com.example.videoconversion.databinding.FragmentSelectVideoBinding
import com.example.videoconversion.viewmodel.SharedViewModel

class SelectVideoFragment : Fragment() {
    private val binding by lazy {
        FragmentSelectVideoBinding.inflate(layoutInflater)
    }

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 1001
    private lateinit var videoAdapter: VideoAdapter
    private val videoList = mutableListOf<Video>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the RecyclerView and Adapter
        setupRecyclerView()

        binding.textView7.setOnClickListener {
            if(sharedViewModel.videoUri.value != null) {
                findNavController().navigate(R.id.action_selectVideoFragment_to_videoProcessing)
            }
            else
            {
                Toast.makeText(requireContext(), "Please select a video", Toast.LENGTH_SHORT).show()
            }
        }
        // Check for permission to read external storage
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission if not granted
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_READ_EXTERNAL_STORAGE
            )
        } else {
            // Load videos if permission is already granted
            loadVideos()
        }
    }

    private fun setupRecyclerView() {
        val sharedViewModel: SharedViewModel by activityViewModels()
        videoAdapter = VideoAdapter(videoList, sharedViewModel)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3) // 3 videos per row
            adapter = videoAdapter
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadVideos() {
        // Load videos from external storage
        val projection = arrayOf(MediaStore.Video.Media._ID, MediaStore.Video.Media.DISPLAY_NAME)
        val selection = null
        val selectionArgs = null
        val sortOrder = "${MediaStore.Video.Media.DATE_ADDED} DESC"

        val query = requireContext().contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val name = cursor.getString(nameColumn)

                val contentUri = ContentUris.withAppendedId(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    id
                )

                videoList.add(Video(contentUri, name))
            }

            videoAdapter.notifyDataSetChanged()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, load videos
                loadVideos()
            } else {
                // Permission denied, show a message to the user
                Toast.makeText(
                    requireContext(),
                    "Permission denied to read external storage",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}