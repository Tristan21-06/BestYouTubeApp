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
    private val onItemClick: (YoutubeVideo) -> Unit // Fonction de callback pour les clics sur un item
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
            intent.putExtra("video_title", video.titre)
            intent.putExtra("video_description", video.description)
            intent.putExtra("video_url", video.url)
            intent.putExtra("video_category", video.categorie)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = videos.size

    inner class YoutubeVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titreTextView: TextView = itemView.findViewById(R.id.textViewTitre)

        fun bind(video: YoutubeVideo) {
            titreTextView.text = video.titre
            itemView.setOnClickListener {
                onItemClick(video) // Appel de la fonction de callback lors du clic
            }
        }
    }
}
