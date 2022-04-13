package com.dhandev.storyapp.view.main

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dhandev.storyapp.R
import com.dhandev.storyapp.ViewModelFactory
import com.dhandev.storyapp.databinding.ActivityMainBinding
import com.dhandev.storyapp.model.UserPreference
import com.dhandev.storyapp.model.getAllStory
import com.dhandev.storyapp.storyItemAdapter
import com.dhandev.storyapp.view.login.LoginActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: storyItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val listStory = ArrayList<getAllStory.ListStoryItem>()
        adapter = storyItemAdapter(listStory)
        adapter.notifyDataSetChanged()

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()


        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]

        binding.apply {
            rvGituser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGituser.setHasFixedSize(true)
            rvGituser.adapter = adapter
            logoutButton.setOnClickListener {
                mainViewModel.logout()
            }
        }

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                binding.nameTextView.text = getString(R.string.greeting, user.name)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        mainViewModel.findStory()
        mainViewModel.getStory().observe(this) {
            if (it != null) {
                adapter.getListStory(it as ArrayList<getAllStory.ListStoryItem>)
            }
        }

    }
}