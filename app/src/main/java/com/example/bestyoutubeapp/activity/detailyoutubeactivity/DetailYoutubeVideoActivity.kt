package com.example.bestyoutubeapp.activity.detailyoutubeactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

import com.example.bestyoutubeapp.R
import com.example.bestyoutubeapp.activity.edityoutubeactivity.EditYoutubeVideoActivity
import com.example.bestyoutubeapp.database.AppDatabase

class DetailYoutubeVideoActivity : AppCompatActivity() {

    private var videoUrl: String? = null
    private lateinit var addVideoLauncher: ActivityResultLauncher<Intent>
    private var videoId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_youtube_video)

        videoId = intent.getLongExtra("video_id", -1)
        loadVideoById()

        val btnHome: Button = findViewById(R.id.btnHome)
        btnHome.setOnClickListener {
            finish()
        }

        addVideoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                loadVideoById()
            }
        }
    }

    private fun ajouterAuxFavoris () {
        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val video = db.youtubeVideoDao().getVideoById(videoId)
            video?.let {
                val newFavoriValue = if (it.favori == 1) 0 else 1
                val updatedVideo = it.copy(favori = newFavoriValue)

                db.youtubeVideoDao().mettreAJourVideo(updatedVideo)

                runOnUiThread {
                    val message = if (newFavoriValue == 1) "Ajouté aux favoris" else "Retiré des favoris"
                    Toast.makeText(this@DetailYoutubeVideoActivity, message, Toast.LENGTH_SHORT).show()

                    recreate()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val db = AppDatabase.getDatabase(this)

        return when (item.itemId) {
            R.id.action_delete_video -> {
                if (videoId != -1L) {
                    lifecycleScope.launch {
                        val video = db.youtubeVideoDao().getVideoById(videoId)
                        db.youtubeVideoDao().supprimerVideo(video)
                        Toast.makeText(this@DetailYoutubeVideoActivity, "Vidéo supprimée", Toast.LENGTH_SHORT).show()

                        finish()
                    }
                }
                true
            }
            R.id.action_edit_video -> {
                if (videoId != -1L) {
                    val intent = Intent(this, EditYoutubeVideoActivity::class.java).apply {
                        putExtra("video_id", videoId)
                    }
                    addVideoLauncher.launch(intent)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun loadVideoById() {
        val titreTextView: TextView = findViewById(R.id.textViewTitre)
        val descriptionTextView: TextView = findViewById(R.id.textViewDescription)
        val urlTextView: TextView = findViewById(R.id.textViewUrl)
        val categorieTextView: TextView = findViewById(R.id.textViewCategorie)

        val btnVoirVideo: Button = findViewById(R.id.btnVoirVideo)
        val btnFavori: Button = findViewById(R.id.btnFavori)

        val db = AppDatabase.getDatabase(this)

        lifecycleScope.launch {
            val video = db.youtubeVideoDao().getVideoById(videoId)

            video?.let {
                titreTextView.text = it.titre
                descriptionTextView.text = it.description
                urlTextView.text = it.url
                categorieTextView.text = it.categorie

                videoUrl = it.url
                btnFavori.text = if (it.favori == 1) "Retirer des favoris" else "Ajouter aux favoris"
            }

            btnVoirVideo.setOnClickListener {
                videoUrl?.let { url -> ouvrirYoutube(url) }
            }

            btnFavori.setOnClickListener {
                ajouterAuxFavoris()
            }
        }
    }

    private fun ouvrirYoutube(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage("com.google.android.youtube")

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
}
