package com.example.notforredditv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.notforredditv2.repository.PostRepository
import com.example.notforredditv2.vo.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostViewModel @Inject constructor(postRepository: PostRepository) : ViewModel(){

    private var postRepo = postRepository

    suspend fun loadPosts() : LiveData<List<Post>>{
        return postRepo.getPosts()
    }

}