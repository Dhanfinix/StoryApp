package com.dhandev.storyapp.api

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.dhandev.storyapp.BuildConfig
import com.dhandev.storyapp.model.*
import com.dhandev.storyapp.view.login.LoginActivity
import com.dhandev.storyapp.view.main.MainActivity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
var token = MainActivity().token1

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
//    @Headers("Authorization: Bearer $token")
    // belum bisa jalan karena 401 unathorized
    //TODO:AUTHORIZATION TOKEN MASUKKAN KE AMBIL DARI DATASTORE
    fun getStories() : Call<getAllStory>

    @Multipart
    @POST("stories")
//    @Headers("Authorization: Bearer ${LoginActivity.token}")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<FileUploadResponse>
}