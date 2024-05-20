package com.example.vkr

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.bottomnav.map.MapFragment
import com.example.vkr.bottomnav.posts.PostsFragment
import com.example.vkr.bottomnav.profile.ProfileFragment
import com.example.vkr.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, MapFragment()).commit()


        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map -> {
                    // Действия при выборе элемента "Map"
                    supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, MapFragment()).commit()
                    true
                }
                R.id.profile -> {
                    // Действия при выборе элемента "Profile"
                    supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, ProfileFragment()).commit()
                    true
                }
                R.id.posts -> {
                    // Действия при выборе элемента "Posts"
                    supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, PostsFragment()).commit()
                    true
                }
                else -> false
            }
        }
    }
}


