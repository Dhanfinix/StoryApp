package com.dhandev.storyapp.api

import okhttp3.Interceptor
import okhttp3.Response

class OAuthInterceptor(private val tokenType: String, private val acceessToken: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $acceessToken").build()

        return chain.proceed(request)
    }

}