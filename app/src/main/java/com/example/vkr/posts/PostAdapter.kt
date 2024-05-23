package com.example.vkr.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vkr.R
import com.example.vkr.databinding.PostItemBinding
import com.squareup.picasso.Picasso

class PostAdapter(private val postList: List<PostItem>, private val onItemClick: (PostItem) -> Unit) :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postList[position]
        holder.bind(post)
    }

    override fun getItemCount() = postList.size

    inner class PostViewHolder(private val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: PostItem) {
            binding.postTitleTv.text = post.postTitle
            if (post.imageLink.isNotEmpty()) {
                Picasso.get().load(post.imageLink).into(binding.postIv)
            } else {
                binding.postIv.setImageResource(R.drawable.post_image) // placeholder image
            }

            binding.root.setOnClickListener {
                onItemClick(post)
            }
        }
    }
}