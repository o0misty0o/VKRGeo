package com.example.vkr

import android.content.Intent
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
            val latitude = intent.getDoubleExtra("latitude", 0.0)
            val longitude = intent.getDoubleExtra("longitude", 0.0)
            val fragment = MapFragment.newInstance().apply {
                arguments = Bundle().apply {
                    putDouble("latitude", latitude)
                    putDouble("longitude", longitude)
                }
            }
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let { handleIntent(it) } // Обработать новый интент
    }

    private fun handleIntent(intent: Intent) {
        val navigateTo = intent.getStringExtra("navigateTo")
        if (navigateTo == "PostsFragment") {
            openFragment(PostsFragment())
        }
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}
