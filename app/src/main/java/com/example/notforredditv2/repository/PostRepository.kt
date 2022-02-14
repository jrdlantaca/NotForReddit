package com.example.notforredditv2.repository

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.notforredditv2.db.PostDao
import com.example.notforredditv2.api.OAuthService
import com.example.notforredditv2.util.Constants
import com.example.notforredditv2.vo.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PostRepository @Inject constructor(
    private val postDao: PostDao,
    private val oAuthService: OAuthService) {

    private var data = mutableListOf<Post>()

    suspend fun getPosts() : LiveData<List<Post>> {
        withContext(Dispatchers.IO){
            val response = oAuthService.getBest()
            if(response.isSuccessful){
                postDao.deleteTable()
                response.body()!!.data!!.children!!.forEachIndexed{index, postChild ->
                    data.add(postChild.data!!)
                }
                postDao.insert(data)
            }
            else{
                withContext(Dispatchers.Main){
                    Log.d(Constants.TAG,"Error getting data")
                }
            }
        }
        return postDao.getPosts()
    }

}