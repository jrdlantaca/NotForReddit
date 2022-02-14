package com.example.notforredditv2.vo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
    @Json(name="access_token")
    val access_token : String? = null,
    @Json(name = "token_type")
    val token_type : String? = null,
    @Json(name="expires_in")
    val expires_in : Int = 0,
    @Json(name="refresh_token")
    val refresh_token : String? = null,
    @Json(name="scope")
    val scope : String? = null
)