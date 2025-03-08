package com.example.bestyoutubeapp.activity.mainactivity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bestyoutubeapp.R
import com.example.bestyoutubeapp.activity.BaseActivity
import com.example.bestyoutubeapp.adapter.YoutubeVideoAdapter
import com.example.bestyoutubeapp.database.AppDatabase
import com.example.bestyoutubeapp.model.YoutubeVideo
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: YoutubeVideoAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        recyclerView = findViewById(R.id.recyclerViewVideos)
        recyclerView.layoutManager = LinearLayoutManager(this)

        db = AppDatabase.getDatabase(this)

        loadVideos()
    }

    private fun loadVideos() {
        lifecycleScope.launch {
            val videos = db.youtubeVideoDao().getToutesVideos()

            adapter = YoutubeVideoAdapter(videos, this@HomeActivity) { video ->
                onVideoClick(video)
            }

            recyclerView.adapter = adapter
        }
    }

    private fun onVideoClick(video: YoutubeVideo) {
        Toast.makeText(this, "Clique sur la vidéo: ${video.titre}", Toast.LENGTH_SHORT).show()
    }

    override fun refreshData() {
        super.refreshData()

        loadVideos()
    }
}
