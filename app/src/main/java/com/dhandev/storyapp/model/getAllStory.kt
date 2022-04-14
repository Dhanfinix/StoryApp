package com.dhandev.storyapp.model

data class getAllStory(
    val listStory: List<ListStoryItem>,
    val error: Boolean,
    val message: String
)
{
    data class ListStoryItem(
        val photoUrl: String,
        val createdAt: String,
        val name: String,
        val description: String,
        val lon: Double,
        val id: String,
        val lat: Double
    )
}

data class FileUploadResponse(
    val error: Boolean,
    val message: String
)