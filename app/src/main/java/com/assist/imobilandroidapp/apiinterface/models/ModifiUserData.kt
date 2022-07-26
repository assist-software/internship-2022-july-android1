package com.assist.imobilandroidapp.apiinterface.models

data class ModifiUserData(
    val id: String?,
    val fullName: String?,
    val email: String?,
    val phone: String?,
    val role: Int,
    val gender: Int,
    val photo: String?,
    val dateOfBirth: String?,
    val address: String?,
    val isActive: Boolean
)
