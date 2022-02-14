package com.example.notforredditv2.api

import com.example.notforredditv2.vo.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.*

interface OAuthService{

    @GET("api/v1/me")
    suspend fun getUser() : Response<User>

    @GET("best")
    suspend fun getBest() : Response<PostResponse>

     @GET("r/{subreddit}/comments/{article}")
    suspend fun getArticleComments(@Path("subreddit") subreddit : String,
                        @Path("article") article : String,
                        @Query("sort") sort : String) : Response<List<CommentResponse>>


   @FormUrlEncoded
   @POST("access_token")
    suspend fun getToken(
        @Field("grant_type") grant_type: String,
        @Field("code") code : String,
        @Field("redirect_uri") redirect_uri : String
    ) : Response<Token>



}