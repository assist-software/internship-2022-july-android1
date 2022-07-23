package com.assist.imobilandroidapp.apiinterface.models

data class RegisterResponse(
    val userID: String,
    val token: String,
    val email: String,
    val fullName: String,
    val role: String
)