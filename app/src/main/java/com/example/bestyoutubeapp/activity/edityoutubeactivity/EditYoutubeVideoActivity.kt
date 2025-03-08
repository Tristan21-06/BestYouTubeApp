package com.example.bestyoutubeapp.activity.edityoutubeactivity

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

import com.example.bestyoutubeapp.R
import com.example.bestyoutubeapp.activity.FormYoutubeVideoActivity
import com.example.bestyoutubeapp.database.AppDatabase
import com.example.bestyoutubeapp.model.YoutubeVideo

class EditYoutubeVideoActivity : FormYoutubeVideoActivity() {
    private var videoId: Long = -1
    
    override fun getView(): Int {
        return R.layout.edit_youtube_video
    }

    override fun loadForm() {
        super.loadForm()

        videoId = intent.getLongExtra("video_id", -1)
        if (videoId != -1L) {
            val db = AppDatabase.getDatabase(this)
            lifecycleScope.launch {
                try {
                    val video = db.youtubeVideoDao().getVideoById(videoId)
                    video?.let {
                        editTitre.setText(it.titre)
                        editDescription.setText(it.description)
                        editUrl.setText(it.url)

                        val selectedCategoryIndex = categories.indexOf(it.categorie)
                        editCategorie.setSelection(selectedCategoryIndex)
                    }
                } catch (e: Exception) {
                    Log.e("EditYoutubeVideo", "Erreur lors du chargement de la vidéo", e)
                }
            }
        }
    }

    override fun saveForm(titre: String, description: String, url: String, categorie: String) {
        val db = AppDatabase.getDatabase(this)
        lifecycleScope.launch {
            val video = db.youtubeVideoDao().getVideoById(videoId)
            val editedVideo = YoutubeVideo(video.id, titre, description, url, categorie, video.favori)
            db.youtubeVideoDao().mettreAJourVideo(editedVideo)

            Toast.makeText(this@EditYoutubeVideoActivity, "Vidéo mise à jour", Toast.LENGTH_SHORT).show()
            super.saveForm(titre, description, url, categorie)
        }
    }
}
