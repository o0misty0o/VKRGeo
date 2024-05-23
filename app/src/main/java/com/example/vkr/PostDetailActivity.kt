package com.example.vkr

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityPostDetailBinding
import com.example.vkr.posts.PostItem
import com.squareup.picasso.Picasso

class PostDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val post: PostItem? = intent.getParcelableExtra("post")
        // Используйте объект post для отображения данных

        post?.let {
            binding.postTitle.text = it.postTitle
            binding.postText.text = it.postText
            binding.coord1.text = it.postCoord1
            binding.coord2.text = it.postCoord2
            Picasso.get().load(it.imageLink).into(binding.postImage)

            binding.goToMapButton.setOnClickListener {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("latitude", binding.coord1.text.toString().toDouble())
                intent.putExtra("longitude", binding.coord2.text.toString().toDouble())
                intent.putExtra("navigateTo", "MapFragment")
                startActivity(intent)
            }
        }
    }
}