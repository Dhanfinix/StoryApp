package com.dhandev.storyapp.api

import com.dhandev.storyapp.model.login
import com.dhandev.storyapp.model.register
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name : String,
        @Field("email") email : String,
        @Field("password") password : String,
    ) : Call<register>

    @POST("login")
    fun login(
        @Field("name") name : String,
        @Field("password") password : String
    ) : Call<login>
}