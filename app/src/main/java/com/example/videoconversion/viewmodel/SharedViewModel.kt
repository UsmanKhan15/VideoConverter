package com.example.videoconversion.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.net.Uri

class SharedViewModel : ViewModel() {

    // Private MutableLiveData to store the video URI
    private val _videoUri = MutableLiveData<Uri>()

    // Public LiveData to expose the video URI
    val videoUri: LiveData<Uri> = _videoUri

    // Function to set the video URI
    fun setVideoUri(uri: Uri) {
        _videoUri.value = uri
    }
}
