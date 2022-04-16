package com.dhandev.storyapp.view.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dhandev.storyapp.R
import com.dhandev.storyapp.databinding.ActivityDetailBinding
import com.dhandev.storyapp.model.getAllStory
import com.dhandev.storyapp.storyItemAdapter
import com.dhandev.storyapp.withDateFormat

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME)
        val image = intent.getStringExtra(IMAGE)
        val date = intent.getStringExtra(DATE).toString()
        val description = intent.getStringExtra(DESCRIPTION)

        binding.apply {
            Glide.with(this@DetailActivity)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerInside()
                .into(imgItemPhoto)
            tvItemUsername.text = "Story oleh : $username"
            tvItemDate.text = "Dikirim pada : ${date.withDateFormat()}"
            tvItemDesc.text = description
        }
    }
    companion object{
        const val USERNAME = "USERNAME"
        const val IMAGE = "IMAGE"
        const val DATE = "DATE"
        const val DESCRIPTION = "DESC"

    }
}