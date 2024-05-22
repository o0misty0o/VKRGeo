package com.example.vkr.posts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkr.R;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter <PostViewHolder>{

    private ArrayList<Post> posts;

    public PostsAdapter(ArrayList<Post> posts){
        this.posts = posts;
    }
    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item, parent, false);

        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.post_tv.setText(posts.get(position).getPost_title());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }
}
