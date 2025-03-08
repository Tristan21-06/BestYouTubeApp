package com.example.bestyoutubeapp.activity.addyoutubeactivity

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

import com.example.bestyoutubeapp.R
import com.example.bestyoutubeapp.activity.FormYoutubeVideoActivity
import com.example.bestyoutubeapp.model.YoutubeVideo
import com.example.bestyoutubeapp.database.AppDatabase

class AddYoutubeVideoActivity : FormYoutubeVideoActivity() {
    override fun getView(): Int {
        return R.layout.add_youtube_video
    }

    override fun saveForm(titre: String, description: String, url: String, categorie: String) {
        val video = YoutubeVideo(0, titre, description, url, categorie, 0)
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            db.youtubeVideoDao().ajouterVideo(video)

            Toast.makeText(this@AddYoutubeVideoActivity, "Vidéo ajoutée", Toast.LENGTH_SHORT).show()
            super.saveForm(titre, description, url, categorie)
        }
    }
}
