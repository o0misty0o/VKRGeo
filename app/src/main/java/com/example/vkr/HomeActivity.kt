package com.example.vkr

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        // Обработайте Intent, чтобы открыть нужный фрагмент
        val navigateTo = intent.getStringExtra("navigateTo")
        if (navigateTo == "PostsFragment") {
            openFragment(PostsFragment())
        } else {
            // Открыть MapFragment по умолчанию
            openFragment(MapFragment())
        }

        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.map -> {
                    // Действия при выборе элемента "Map"
                    openFragment(MapFragment())
                    true
                }
                R.id.profile -> {
                    // Действия при выборе элемента "Profile"
                    openFragment(ProfileFragment())
                    true
                }
                R.id.posts -> {
                    // Действия при выборе элемента "Posts"
                    openFragment(PostsFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}
