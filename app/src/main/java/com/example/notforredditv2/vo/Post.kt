package com.example.notforredditv2.vo

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "post_table")
@JsonClass(generateAdapter = true)
data class Post(
    @PrimaryKey
    @NonNull
    @Json(name = "id")
    val id : String,
    @Json(name = "score")
    val score : Int = 0,
    @Json(name = "author")
    val author : String? = null,
    @Json(name = "title")
    val title : String? = null,
    @Json(name = "selftext")
    val selftext : String? = null,
    @Json(name= "url")
    val url : String? = null,
    @Json(name = "subreddit")
    val subreddit : String? = null,
    @Json(name = "thumbnail")
    val thumbnail : String? = null,
    @Json(name = "num_comments")
    val numComments : Int = 0,
    @Json(name = "ups")
    val ups : Int = 0,
    @Json(name = "downs")
    val downs : Int = 0,
    @Json(name = "created")
    val created : Long = 0
)


@JsonClass(generateAdapter = true)
data class PostResponse(
    @Json(name = "kind")
    val kind : String? = null,
    @Json(name = "data")
    val data : PostData? = null
)

@JsonClass(generateAdapter = true)
data class PostData(
    @Json(name = "children")
    val children : List<PostChild>? = null
)

@JsonClass(generateAdapter = true)
data class PostChild(
    @Json(name = "data")
    val data : Post? = null
)