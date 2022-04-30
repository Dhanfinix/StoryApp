package com.dhandev.storyapp.view.detail

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Detail Story"
        val story = intent.getParcelableExtra<getAllStory.ListStoryItem>("Story") as getAllStory.ListStoryItem
        binding.apply {
            Glide.with(applicationContext)
                .load(story.photoUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .centerInside()
                .into(imgItemPhoto)
            tvItemUsername.text = "Story oleh : ${story.name}"
            tvItemDate.text = "Dikirim pada : ${story.createdAt.withDateFormat()}"
            tvItemDesc.text = story.description
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}