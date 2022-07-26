package com.assist.imobilandroidapp.apiinterface.models

data class SpecificUser(
    val id: String?,
    val fullName: String?,
    val email: String?,
    val phone: String?,
    val role: String?,
    val photo: String?,
    val gender: Int,
    val dateOfBirth: String?,
    val address: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val isActive: Boolean
)