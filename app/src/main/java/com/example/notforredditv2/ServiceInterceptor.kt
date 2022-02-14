package com.example.notforredditv2

import com.example.notforredditv2.di.ApplicationScope
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

@ApplicationScope
class ServiceInterceptor : Interceptor{

    private lateinit var access_token : String

    @Inject
    constructor()

    fun setAccessToken(access_token : String) {
        this.access_token = access_token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("User-Agent", "NotForReddit by Janemboo" )
            .addHeader("Authorization", "bearer $access_token")
            .build()

        return chain.proceed(newRequest)
    }

}