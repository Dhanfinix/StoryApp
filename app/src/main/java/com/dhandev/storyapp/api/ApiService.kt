package com.dhandev.storyapp.api

import com.dhandev.storyapp.BuildConfig
import com.dhandev.storyapp.model.getAllStory
import com.dhandev.storyapp.model.login
import com.dhandev.storyapp.model.register
import com.dhandev.storyapp.view.login.LoginActivity
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String,
    ) : Call<register>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<login>

    @GET("stories")
    @Headers("Authorization: Bearer ${LoginActivity.token}")
    // belum bisa jalan karena 401 unathorized
    fun getStories() : Call<getAllStory>
}