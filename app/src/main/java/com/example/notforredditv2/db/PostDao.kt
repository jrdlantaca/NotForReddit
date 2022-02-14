package com.example.notforredditv2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notforredditv2.vo.Post

@Dao
interface PostDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts : List<Post>)

    @Query("SELECT * FROM post_table")
    fun getPosts() : LiveData<List<Post>>

    @Query("DELETE FROM post_table")
    fun deleteTable()

}