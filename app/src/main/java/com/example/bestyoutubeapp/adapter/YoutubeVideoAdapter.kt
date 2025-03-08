package com.example.bestyoutubeapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bestyoutubeapp.R
import com.example.bestyoutubeapp.activity.detailyoutubeactivity.DetailYoutubeVideoActivity
import com.example.bestyoutubeapp.model.YoutubeVideo

class YoutubeVideoAdapter(
    private val videos: List<YoutubeVideo>,
    private val context: Context,
    private val onItemClick: (YoutubeVideo) -> Unit
) : RecyclerView.Adapter<YoutubeVideoAdapter.YoutubeVideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YoutubeVideoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_youtube_video, parent, false)
        return YoutubeVideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: YoutubeVideoViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailYoutubeVideoActivity::class.java)
            intent.putExtra("video_id", video.id)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = videos.size

    inner class YoutubeVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titreView: TextView = itemView.findViewById(R.id.textTitre)
        private val descriptionView: TextView = itemView.findViewById(R.id.textDescription)

        fun bind(video: YoutubeVideo) {
            titreView.text = video.titre
            descriptionView.text = video.description

            itemView.setOnClickListener {
                onItemClick(video)
            }
        }
    }
}
