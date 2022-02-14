package com.example.notforredditv2.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notforredditv2.vo.Comment
import com.example.notforredditv2.vo.Post

@Database(entities = [Post::class, Comment::class], version = 2, exportSchema = false)
abstract class RedditDatabase : RoomDatabase(){
    abstract fun postDao() : PostDao
    abstract fun commentDao() : CommentDao
}