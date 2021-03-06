package com.dhandev.storyapp.api

import com.dhandev.storyapp.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    fun getStories(
        @Header("Authorization") Authorization : String,
    ) : Call<getAllStory>

    @Multipart
    @POST("stories")
    fun uploadImage(
        @Header("Authorization") Authorization : String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>
}