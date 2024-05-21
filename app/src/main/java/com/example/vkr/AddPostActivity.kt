package com.example.vkr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityAddPostBinding


class AddPostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPostBinding
    private val PICK_IMAGE_REQUEST = 1
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

        // Находим ImageView по его id
        val imageView = binding.newpostIv
        // Устанавливаем слушатель нажатия на imageView
        imageView.setOnClickListener {
            openGallery()
        }

    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Выберите изображение"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            binding.newpostIv.setImageURI(imageUri)
        }
    }
}