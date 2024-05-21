package com.example.vkr.posts;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vkr.R;

public class PostViewHolder extends RecyclerView.ViewHolder {


    ImageView post_iv;
    TextView post_tv;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        post_iv = itemView.findViewById(R.id.post_iv);
        post_tv = itemView.findViewById(R.id.post_title_tv);
    }
}
