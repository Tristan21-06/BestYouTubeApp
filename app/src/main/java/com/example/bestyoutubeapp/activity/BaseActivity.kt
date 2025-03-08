package com.example.bestyoutubeapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.bestyoutubeapp.R
import com.example.bestyoutubeapp.activity.addyoutubeactivity.AddYoutubeVideoActivity
import com.example.bestyoutubeapp.activity.mainactivity.HomeActivity

open class BaseActivity : AppCompatActivity() {

    private lateinit var addVideoLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addVideoLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                refreshData()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_add_video -> {
                val intent = Intent(this, AddYoutubeVideoActivity::class.java)
                addVideoLauncher.launch(intent)
                true
            }
            R.id.action_list_video -> {
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    open fun refreshData() {}
}
