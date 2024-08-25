package com.example.videoconversion

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.videoconversion.viewmodel.SharedViewModel

class VideoAdapter(
    private val videos: List<Video>,
    private val sharedViewModel: SharedViewModel
) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val video = videos[position]
        holder.bind(video, position == selectedPosition)

        holder.itemView.setOnClickListener {
            if (position == selectedPosition) {
                // Deselect if the same item is clicked
                sharedViewModel.setVideoUri(Uri.EMPTY)  // Clear the URI
                selectedPosition = RecyclerView.NO_POSITION
            } else {
                // Select the new item
                sharedViewModel.setVideoUri(video.uri)
                val previousPosition = selectedPosition
                selectedPosition = position
                notifyItemChanged(previousPosition) // Unselect previous
            }
            notifyItemChanged(position) // Update current item (select/deselect)
        }
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoName: TextView = itemView.findViewById(R.id.videoName)
        private val videoThumbnail: ImageView = itemView.findViewById(R.id.videoThumbnail)
        private val selectUnselectImage: ImageView = itemView.findViewById(R.id.select_unselect_image)

        fun bind(video: Video, isSelected: Boolean) {
            videoName.text = video.name

            // Load video thumbnail using Glide or similar library
            Glide.with(itemView.context)
                .load(video.uri)
                .thumbnail(0.1f)
                .into(videoThumbnail)

            // Update the selection indicator
            val imageResId = if (isSelected) R.drawable.icon_selected else R.drawable.icon_unselected
            selectUnselectImage.setImageResource(imageResId)
        }
    }
}