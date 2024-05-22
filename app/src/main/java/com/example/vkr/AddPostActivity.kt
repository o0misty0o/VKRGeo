package com.example.vkr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityAddPostBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class AddPostActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAddPostBinding
    private val PICK_IMAGE_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

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

        binding.publishButton.setOnClickListener{
            val title = binding.titleTv.text.toString()
            val text = binding.textTv.text.toString()
            if (checkFields()) {
                //FirebaseAuth.getInstance().
            }
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

    private fun checkFields(): Boolean{
        val title = binding.titleEt.text.toString()
        if(binding.titleEt.text.toString().isEmpty()){
            Toast.makeText(applicationContext,"Title cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}