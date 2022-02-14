package com.example.notforredditv2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.notforredditv2.vo.Comment

@Dao
interface CommentDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(comments : List<Comment>)

    @Query("SELECT * FROM comment_table")
    fun getComments() : LiveData<List<Comment>>

    @Query("DELETE FROM comment_table")
    fun deleteTable()

}