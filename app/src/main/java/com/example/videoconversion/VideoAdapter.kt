package com.example.videoconversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class VideoAdapter(private val videos: List<Video>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoName: TextView = itemView.findViewById(R.id.videoName)
        private val videoThumbnail: ImageView = itemView.findViewById(R.id.videoThumbnail)

        fun bind(video: Video) {
            videoName.text = video.name

            // Load video thumbnail using Glide or similar library
            Glide.with(itemView.context)
                .load(video.uri)
                .thumbnail(0.1f)
                .into(videoThumbnail)

            itemView.setOnClickListener {
                // Handle video click
            }
        }
    }
}
