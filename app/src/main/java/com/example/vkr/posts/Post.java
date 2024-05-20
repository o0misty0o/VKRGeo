package com.example.vkr.posts;

public class Post {
    private String post_id,post_title,post_text,user;

    public Post(String post_id, String post_title, String post_text, String user) {
        this.post_id = post_id;
        this.post_title = post_title;
        this.post_text = post_text;
        this.user = user;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_title() {
        return post_title;
    }

    public void setPost_title(String post_title) {
        this.post_title = post_title;
    }

    public String getPost_text() {
        return post_text;
    }

    public void setPost_text(String post_text) {
        this.post_text = post_text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
