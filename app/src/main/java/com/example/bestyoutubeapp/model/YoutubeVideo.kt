package com.example.bestyoutubeapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "youtube_videos")
data class YoutubeVideo(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val titre: String,
    val description: String,
    val url: String,
    val categorie: String,
    val favori: Int = 0 // 0 = pas favori, 1 = favori
)
