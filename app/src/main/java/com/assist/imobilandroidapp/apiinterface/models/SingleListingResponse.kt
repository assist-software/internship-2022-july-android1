package com.assist.imobilandroidapp.apiinterface.models

import java.util.*

data class SingleListingResponse(
    val id: String,
    val title: String,
    val description: String,
    val shortDescription: String,
    val location: String,
    val price: Double,
    val images: ArrayList<String>,
    val category: Int,
    val authorId: String,
    val createdAt: String?,
    val viewCounter: Int,
    val phoneNumber: String,
    val isFavourite: Boolean
)
