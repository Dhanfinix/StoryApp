package com.dhandev.storyapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class getAllStory(
    val listStory: List<ListStoryItem>,
    val error: Boolean,
    val message: String
)
{
    @Parcelize
    data class ListStoryItem(
        val photoUrl: String,
        val createdAt: String,
        val name: String,
        val description: String,
        val lon: Double,
        val id: String,
        val lat: Double
    ) : Parcelable
}

data class FileUploadResponse(
    val error: Boolean,
    val message: String
)