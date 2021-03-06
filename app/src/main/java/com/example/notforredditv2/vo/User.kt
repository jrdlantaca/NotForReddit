package com.example.notforredditv2.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "name")
    val name : String? = null
)