package com.example.bestyoutubeapp.dao

import androidx.room.*
import com.example.bestyoutubeapp.model.YoutubeVideo

@Dao
interface YoutubeVideoDao {
    @Insert
    suspend fun ajouterVideo(video: YoutubeVideo)

    @Query("SELECT * FROM youtube_videos")
    suspend fun getToutesVideos(): List<YoutubeVideo>

    @Query("SELECT * FROM youtube_videos WHERE id = :videoId LIMIT 1")
    suspend fun getVideoById(videoId: Long): YoutubeVideo

    @Query("SELECT * FROM youtube_videos WHERE favori = 1")
    suspend fun getFavoris(): List<YoutubeVideo>

    @Update
    suspend fun mettreAJourVideo(video: YoutubeVideo)

    @Delete
    suspend fun supprimerVideo(video: YoutubeVideo)
}
