package com.dhandev.storyapp.view.add

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dhandev.storyapp.api.ApiConfig
import com.dhandev.storyapp.model.FileUploadResponse
import com.dhandev.storyapp.model.UserModel
import com.dhandev.storyapp.model.UserPreference
import com.dhandev.storyapp.model.getAllStory
import com.dhandev.storyapp.view.main.MainActivity
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewModel(private val pref: UserPreference) : ViewModel() {
    private val _listStory = MutableLiveData<FileUploadResponse>()
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun uploadStory(token : String, imageMultipart : MultipartBody.Part, description : RequestBody){
        val service = ApiConfig.getApiService().uploadImage("Bearer $token",imageMultipart, description)
        service.enqueue(object: Callback<FileUploadResponse> {
            override fun onResponse(
                call: Call<FileUploadResponse>,
                response: Response<FileUploadResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null && !responseBody.error) {
                        _listStory.value = (response.body())
                    }
                }
            }

            override fun onFailure(call: Call<FileUploadResponse>, t: Throwable) {
                Log.d("Failure", t.message.toString())
            }

        })
    }
    fun getUploaded() : LiveData<FileUploadResponse>{
        return _listStory
    }
}