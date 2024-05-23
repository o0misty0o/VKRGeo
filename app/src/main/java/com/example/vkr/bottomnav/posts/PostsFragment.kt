package com.example.vkr.bottomnav.posts

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vkr.AddPostActivity
import com.example.vkr.HomeActivity
import com.example.vkr.PostDetailActivity
import com.example.vkr.databinding.FragmentPostsBinding
import com.example.vkr.posts.PostAdapter
import com.example.vkr.posts.PostItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var postAdapter: PostAdapter
    private val postList: MutableList<PostItem> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        // Настройка RecyclerView
        binding.postsRv.layoutManager = LinearLayoutManager(context)

        postAdapter = PostAdapter(postList) { post ->
            val intent = Intent(activity, PostDetailActivity::class.java)
            intent.putExtra("post", post)
            startActivity(intent)
        }
        binding.postsRv.adapter = postAdapter

        // Загрузка данных из Firebase
        loadPosts()

        // Установите слушатель нажатий на кнопку
        binding.addpostBtn.setOnClickListener {
            val intent = Intent(activity, AddPostActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun loadPosts(){
        val posts: ArrayList<PostItem> = arrayListOf()

        // Создание объектов PostItem
        val post1 = PostItem().apply {
            imageLink = ""
            postTitle = "Post Title 1"
            postText = "This is the text of the first post."
            postCoord1 = "Coord1_1"
            postCoord2 = "Coord1_2"
            date = "2024-05-23"
            userName = "User1"
            userId = "UserId1"
        }

        val post2 = PostItem().apply {
            imageLink = ""
            postTitle = "Post Title 2"
            postText = "This is the text of the second post."
            postCoord1 = "Coord2_1"
            postCoord2 = "Coord2_2"
            date = "2024-05-24"
            userName = "User2"
            userId = "UserId2"
        }

        // Добавление объектов в список
        posts.add(post1)
        posts.add(post2)


        // Установка LayoutManager
        binding.postsRv.layoutManager = LinearLayoutManager(context)

        // Установка адаптера
        val adapter = PostAdapter(posts) { post ->
            // Обработка клика на элемент
            onPostItemClick(post)
        }
        binding.postsRv.adapter = adapter

    }

    private fun onPostItemClick(post: PostItem) {
        // Обработка клика на элемент
        // Например, переход к деталям поста или что-то еще
        // Здесь можно реализовать навигацию или отображение деталей поста
        // Пример:
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
    }

//    private fun loadPosts() {
//        val userId = auth.currentUser?.uid
//        println("from fun loadPosts: "+userId)
//        userId?.let {
//            database.child("posts").orderByChild("userId").equalTo(it)
//                .addValueEventListener(object : ValueEventListener {
//                    @SuppressLint("NotifyDataSetChanged")
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        postList.clear()
//                        for (postSnapshot in snapshot.children) {
//                            val post = postSnapshot.getValue(PostItem::class.java)
//                            if (post != null) {
//                                postList.add(post)
//                                Log.e("PostsFragment", "No problem with post finding!!")
//                            } else {
//                                Log.e("PostsFragment", "Post is null")
//                            }
//                        }
//                        postAdapter.notifyDataSetChanged()
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        Log.e("PostsFragment", "Failed to load posts", error.toException())
//                    }
//                })
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}