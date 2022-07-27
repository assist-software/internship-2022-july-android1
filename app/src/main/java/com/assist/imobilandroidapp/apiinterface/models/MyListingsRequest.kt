package com.assist.imobilandroidapp.apiinterface.models

data class MyListingsRequest(
    val id: String?,
    val category: String?,
    val price: String?,
    val locations: List<String>?,
    val orderBy: String?,
    val pageIndex: Int?,
    val userId: String?
)
