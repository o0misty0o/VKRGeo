package com.example.vkr

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vkr.databinding.ActivityAddPostBinding
import com.example.vkr.posts.PostItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date
import android.util.Log
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class AddPostActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityAddPostBinding
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri: Uri? = null
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        // Получение координат из Intent
        latitude = intent.getDoubleExtra("latitude", 0.0)
        longitude = intent.getDoubleExtra("longitude", 0.0)

        // Установка координат в TextView
        val coordTextView = binding.coord1
        coordTextView.text = "$latitude, $longitude"

        binding.backButton.setOnClickListener {
            //Intent для перехода на HomeActivity с флагом возврата на PostsFragment
            val intent = Intent(this, HomeActivity::class.java).apply {
                putExtra("navigateTo", "PostsFragment")
            }
            startActivity(intent)
        }

        // Устанавливаем слушатель нажатия на imageView
        binding.newpostIv.setOnClickListener {
            openGallery()
        }

        binding.publishButton.setOnClickListener {
            if (checkFields()) {
                uploadImage(imageUri)
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
            imageUri = data.data
            binding.newpostIv.setImageURI(imageUri)
        }
    }

    private fun getBitmapFromImageView(imageView: ImageView): Bitmap? {
        val drawable = imageView.drawable
        return if (drawable != null) {
            val bitmapDrawable = drawable as BitmapDrawable
            bitmapDrawable.bitmap
        } else {
            null
        }
    }

    private fun createAndPublishPost(imageLink: String) {
        val postTitle = binding.titleEt.text.toString()
        val postText = binding.textTv.text.toString()
        val postCoord1 = latitude.toString()
        val postCoord2 = longitude.toString()
        val user = auth.currentUser

        if (user != null) {
            val userName = user.displayName ?: user.email ?: "Anonymous"
            val userID = user.uid
            val postItem = PostItem().apply {
                this.imageLink = imageLink
                this.postTitle = postTitle
                this.postText = postText
                this.postCoord1 = postCoord1
                this.postCoord2 = postCoord2
                this.date = Date().toString()
                this.userName = userName
                this.userId = userID
            }

            // Сохранить postItem в Firebase
            val postsRef = FirebaseDatabase.getInstance().reference.
            child("posts").child(postItem.uuid)
            postsRef.setValue(postItem).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Post published successfully",
                        Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to publish post",
                        Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(this, "User not authenticated",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadImage(filePath: Uri?) {
        filePath?.let {
            val uid = FirebaseAuth.getInstance().currentUser?.uid
            uid?.let {
                val storageReference = FirebaseStorage.getInstance().getReference("postsImages/$uid")
                storageReference.putFile(filePath)
                    .addOnSuccessListener { taskSnapshot ->
                        Toast.makeText(this, "Photo upload complete", Toast.LENGTH_SHORT).show()

                        storageReference.downloadUrl.addOnSuccessListener { uri ->
                            createAndPublishPost(uri.toString())
                        }.addOnFailureListener { exception ->
                            Log.e("AddPostActivity", "Failed to get download URL", exception)
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("AddPostActivity", "Photo upload failed", exception)
                    }
            }
        }
    }

    private fun checkFields(): Boolean {
        if (binding.titleEt.text.toString().isEmpty()) {
            Toast.makeText(applicationContext, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
