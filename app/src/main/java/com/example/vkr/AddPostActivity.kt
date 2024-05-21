package com.example.vkr

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.vkr.databinding.ActivityAddPostBinding
import com.example.vkr.databinding.ActivityHomeBinding

class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            //Intent для перехода на HomeActivity с флагом возврата на PostsFragment
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("navigateTo", "PostsFragment")
            }
            startActivity(intent)
        }

    }
}