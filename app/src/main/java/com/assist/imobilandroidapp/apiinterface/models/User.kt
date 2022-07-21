package com.assist.imobilandroidapp.apiinterface.models

import androidx.annotation.Nullable

data class User(
    val email: String?,
    val password: String?,
    val nameDevice: String?,
    val deviceType: String?,
    val location: String?,
    val status: Boolean,
    val token: String?
)
