package com.example.vkr.bottomnav.posts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vkr.AddPostActivity
import com.example.vkr.databinding.FragmentPostsBinding


class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)

        // Установите слушатель нажатий на кнопку
        binding.addpostBtn.setOnClickListener {
            // Создайте Intent для перехода на новое Activity
            val intent = Intent(activity, AddPostActivity::class.java)
            intent.putExtra("window", "posts")
            startActivity(intent)
        }

        return binding.root
    }



}