package com.assist.imobilandroidapp.apiinterface.models

data class ListingFromDBObject (
    val id: String,
    val title: String,
    val shortDescription: String,
    val location: String,
    val price: Double,
    val images: String,
    val category: Int,
    val featured: Boolean,
    val phoneNumber: String
    )
