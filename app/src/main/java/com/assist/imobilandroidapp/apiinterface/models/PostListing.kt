package com.assist.imobilandroidapp.apiinterface.models

data class PostListing(
    val title: String,
    val description: String,
    val location: String,
    val price: Double,
    val images: ArrayList<String>,
    val category: Int,
    val authorId: String,
    val featured: Boolean,
    val phoneNumber: String
)
