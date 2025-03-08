package com.example.bestyoutubeapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import com.example.bestyoutubeapp.R

open class FormYoutubeVideoActivity: AppCompatActivity() {
    open lateinit var editTitre: EditText
    open lateinit var editDescription: EditText
    open lateinit var editUrl: EditText
    open lateinit var editCategorie: Spinner

    val categories = arrayOf("Sport", "Music", "Comédie", "Cuisine", "Aventure")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getView())

        editTitre = findViewById(R.id.editTitre)
        editDescription = findViewById(R.id.editDescription)
        editUrl = findViewById(R.id.editUrl)

        editCategorie = findViewById(R.id.editCategorie)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        editCategorie.adapter = adapter

        loadForm()
    }

    open fun loadForm() {
        val btnSave: Button = findViewById(R.id.btnSave)
        btnSave.setOnClickListener {
            val titre = editTitre.text.toString()
            val description = editDescription.text.toString()
            val url = editUrl.text.toString()
            val categorie = editCategorie.selectedItem.toString()

            if (titre.isEmpty() || description.isEmpty() || url.isEmpty()) {
                Toast.makeText(this, "Tous les champs sont requis", Toast.LENGTH_SHORT).show()
            } else {
                saveForm(titre, description, url, categorie)
            }
        }

        val btnAnnuler: Button = findViewById(R.id.btnAnnuler)
        btnAnnuler.setOnClickListener {
            finish()
        }
    }

    // call parent function after data is saved
    open fun saveForm (titre: String, description: String, url: String, categorie: String) {
        // reloads data on previous screen
        val intent = Intent()
        setResult(RESULT_OK, intent)
        finish()
    }

    open fun getView(): Int {
        throw Error("Il faut définir la fonction FormYoutubeVideoActivity::getView() dans les sous-classes")
    }
}