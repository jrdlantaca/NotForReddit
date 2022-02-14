package com.example.notforredditv2.vo

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(tableName = "comment_table")
@JsonClass(generateAdapter = true)
data class Comment(
    @PrimaryKey
    @NonNull
    @Json(name = "id")
    val id: String,
    @Json(name = "score")
    val score: Int = 0,
    @Json(name="author")
    val author : String? = null,
    @Json(name = "subreddit")
    val subreddit : String? = null,
    @Json(name = "body")
    val body : String? = null,
    @Json(name = "ups")
    val ups : Int = 0,
    @Json(name = "downs")
    val downs : Int = 0,
    @Json(name = "created")
    val created : Long = 0
)

@JsonClass(generateAdapter = true)
data class CommentResponse(
    @Json(name="kind")
    val kind : String? = null,
    @Json(name = "data")
    val data : CommentData? = null
)

@JsonClass(generateAdapter = true)
data class CommentData(
    @Json(name = "children")
    val children : List<CommentChild>? = null
)

@JsonClass(generateAdapter = true)
data class CommentChild(
    @Json(name = "kind")
    val kind : String? = null,
    @Json(name = "data")
    val data : Comment? = null
)

