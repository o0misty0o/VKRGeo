package com.example.vkr.posts

import com.google.firebase.database.Exclude
import java.util.*
import kotlin.collections.ArrayList

class PostItem{
    var uuid: String = UUID.randomUUID().toString()
    var imageLink: String = ""
    var postTitle: String = ""
    var postText: String = ""
    var date:String = ""
    var userId: String = ""
    var userName: String = ""
    var timeStamp: Date? = null
}