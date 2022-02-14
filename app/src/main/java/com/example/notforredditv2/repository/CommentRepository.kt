package com.example.notforredditv2.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.notforredditv2.api.OAuthService
import com.example.notforredditv2.db.CommentDao
import com.example.notforredditv2.util.Constants
import com.example.notforredditv2.vo.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CommentRepository @Inject constructor(
    private val commentDao: CommentDao,
    private val oAuthService: OAuthService
) {
    private var data = mutableListOf<Comment>()

    suspend fun getComments(subreddit: String, article: String): LiveData<List<Comment>> {
        withContext(Dispatchers.IO) {
            val response = oAuthService.getArticleComments(subreddit, article, "best")
            if (response.isSuccessful) {
                commentDao.deleteTable()
                response.body()!!.forEachIndexed{index, commentResponse ->
                    commentResponse.data!!.children!!.forEachIndexed{index, commentChild ->
                        if(commentChild.kind == "t1" || commentChild.kind == "more"){
                            data.add(commentChild.data!!)
                        }
                    }
                }
                commentDao.insert(data)
            }
            else{
                withContext(Dispatchers.Main){
                    Log.d(Constants.TAG, "Error getting data")
                }
            }
        }
        return commentDao.getComments()
    }

}