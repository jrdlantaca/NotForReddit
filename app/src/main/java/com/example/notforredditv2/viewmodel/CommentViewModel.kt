package com.example.notforredditv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notforredditv2.repository.CommentRepository
import com.example.notforredditv2.vo.Comment
import javax.inject.Inject

class CommentViewModel @Inject constructor(commentRepository: CommentRepository) : ViewModel() {

    private var commentRepo = commentRepository
    private lateinit var subreddit : String
    private lateinit var article : String

    fun setArgs(subreddit : String, article : String){
        this.article = article
        this.subreddit = subreddit
    }

    suspend fun loadComments() : LiveData<List<Comment>>{
        return commentRepo.getComments(subreddit,article)
    }

}