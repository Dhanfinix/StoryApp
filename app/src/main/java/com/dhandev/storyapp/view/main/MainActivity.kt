package com.dhandev.storyapp.view.main

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
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
import com.dhandev.storyapp.view.add.AddStoryActivity
import com.dhandev.storyapp.view.detail.DetailActivity
import com.dhandev.storyapp.view.login.LoginActivity
import java.io.File

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


        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainViewModel::class.java]


        binding.apply {
            rvGituser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvGituser.setHasFixedSize(true)
            rvGituser.adapter = adapter
            fab.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddStoryActivity::class.java))
            }
        }

        mainViewModel.getUser().observe(this) { user ->
            if (user.isLogin) {
                mainViewModel.findStory(user.token)
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

        mainViewModel.getStory().observe(this) {
            if (it != null) {
                adapter.getListStory(it as ArrayList<getAllStory.ListStoryItem>)
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu1 -> {
                mainViewModel.logout()
                return true
            }
            else -> return true
        }
    }
}